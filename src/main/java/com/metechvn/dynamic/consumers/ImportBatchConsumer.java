package com.metechvn.dynamic.consumers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metechvn.resource.repositories.ImportFileRepository;
import com.metechvn.dynamic.entities.DynamicEntity;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class ImportBatchConsumer {

    private final ObjectMapper objectMapper;
    private final DynamicEntityTypeRepository entityTypeRepository;
    private final ImportFileRepository importFileRepository;
    private final EntityManagerFactory emf;

    public ImportBatchConsumer(
            ObjectMapper objectMapper,
            ImportFileRepository importFileRepository,
            DynamicEntityTypeRepository entityTypeRepository,
            EntityManagerFactory emf) {
        this.objectMapper = objectMapper;
        this.importFileRepository = importFileRepository;
        this.entityTypeRepository = entityTypeRepository;
        this.emf = emf;
    }

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

    private void tryToUpdateImportStatus(String fileName, String jobId, int successRow, int errorRows) {
        try {
            var importFile = importFileRepository.findIncludeStatusById(UUID.fromString(jobId));
            if (importFile == null || importFile.getImportStatus() == null) {
                log.warn("Cannot find import file named {} with jobId {}", fileName, jobId);
                return;
            }

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


                for (var row : batches) {
                    var entity = new DynamicEntity();
                    entity.setEntityType(entityType);
                    entity.setTenant(tenant);

                    // TODO: Validate entities here

                    for (var entry : row.entrySet()) {
                        var property = entityType.getProperty(entry.getKey());
                        if (property == null) continue;


                        entity.set(property, entry.getValue());
                    }
                    session.persist(entity);
                }

                transaction.commit();

                tryToUpdateImportStatus(fileName, jobId, batches.size(), 0);
            }
        };
    }
}
