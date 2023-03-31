package com.metechvn.dynamic.consumers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metechvn.resource.repositories.ImportFileRepository;
import com.metechvn.dynamic.entities.DynamicEntity;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import com.metechvn.validators.IDynamicTypeValidator;
import com.metechvn.validators.dtos.DynamicTypeValidator;
import com.metechvn.validators.dtos.DynamicTypeValidatorDto;
import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImportBatchConsumer {

    private final ObjectMapper objectMapper;
    private final IDynamicTypeValidator dynamicTypeValidator;
    private final DynamicEntityTypeRepository entityTypeRepository;
    private final ImportFileRepository importFileRepository;
    private final EntityManagerFactory emf;

    @KafkaListener(
            topics = "MKT.JOB.ImportExcelBatch",
            groupId = "${spring.kafka.client-id}",
            containerFactory = "objListenerContainerFactory")
    public void onBatch(ConsumerRecord<Object, Map<String, Object>> cr) {
        if (cr.value() == null) {
            return;
        }

        this.importThread(cr.value()).run();
    }

    private void tryToUpdateImportStatus(String fileName, String jobId, int totalRows, int successRow, int errorRows) {
        try {
            var importFile = importFileRepository.findIncludeStatusById(UUID.fromString(jobId));
            if (importFile == null || importFile.getImportStatus() == null) {
                log.warn("Cannot find import file named {} with jobId {}", fileName, jobId);
                return;
            }

            if (importFile.getTotalRecords() == null
                    || importFile.getTotalRecords() <= 0) importFile.setTotalRecords((long) totalRows);

            importFile.getImportStatus().incError(errorRows);
            importFile.getImportStatus().incSuccess(successRow);

            importFileRepository.save(importFile);
        } catch (Exception e) {
            log.error("Cannot update import status job {} file {}. Trace {}", jobId, fileName, e.getMessage());
        }
    }

    private Runnable importThread(Map<String, Object> batchData) {
        return () -> {
            var tenant = (String) batchData.get("tenant");
            var jobId = (String) batchData.get("jobId");
            var fileName = (String) batchData.get("fileName");

            var totalRows = 0;
            if (batchData.get("totalRows") instanceof Integer rows) totalRows = rows;

            var entityType = entityTypeRepository.findIncludeRelationsByCode((String) batchData.get("entityType"));
            if (entityType == null) {
                return;
            }

            var batches = objectMapper.convertValue(batchData.get("batches"), new TypeReference<List<Map<String, Object>>>() {
            });

            var sessionFactory = emf.unwrap(SessionFactory.class);
            try (var session = sessionFactory.openSession()) {
                Transaction transaction;
                try {
                    transaction = session.beginTransaction();
                } catch (Exception e) {
                    transaction = session.getTransaction();
                }

                var validators = entityType.getProperties().entrySet()
                        .stream()
                        .filter(e -> StringUtils.hasText(e.getValue().getProperty().getValidators()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> DynamicTypeValidator.fromJson(e.getValue().getProperty().getValidators())
                        ));

                var exceptionRows = new ArrayList<Map<String, Object>>();

                for (var row : batches) {
                    var entity = new DynamicEntity();
                    entity.setEntityType(entityType);
                    entity.setTenant(tenant);

                    var validationResult = true;
                    for (var entry : entityType.getProperties().entrySet()) {
                        var value = row.get(entry.getKey());

                        try {
                            dynamicTypeValidator.test(
                                    new DynamicTypeValidatorDto(entry.getKey(), value, validators.get(entry.getKey()))
                            );
                        } catch (DynamicTypeValidatorException e) {
                            validationResult = false;
                            continue;
                        }

                        entity.set(entry.getValue(), entry.getValue());
                    }

                    if (!validationResult) {
                        exceptionRows.add(row);
                        continue;
                    }

                    session.persist(entity);
                }

                transaction.commit();

                tryToUpdateImportStatus(fileName, jobId, totalRows, batches.size(), exceptionRows.size());

                // TODO: push error rows to kafka to send end-user
            }
        };
    }
}
