package com.metechvn.dynamic.consumers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metechvn.config.KafkaSession;
import com.metechvn.dynamic.entities.DynamicEntity;
import com.metechvn.dynamic.entities.ImportStatus;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import com.metechvn.dynamic.repositories.ImportStatusRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ImportBatchConsumer {

    private final ObjectMapper objectMapper;
    private final ImportStatusRepository importStatusRepository;
    private final DynamicEntityTypeRepository entityTypeRepository;
    private final EntityManagerFactory emf;

    @KafkaSession
    @KafkaListener(
            topics = "MKT.JOB.ImportExcelBatch",
            groupId = "${spring.kafka.client-id}",
            containerFactory = "objListenerContainerFactory")
    public void onBatch(ConsumerRecord<Object, Map<String, Object>> cr) throws Exception {
        if (cr.value() == null) {
            return;
        }

        var batchData = cr.value();

        var tenant = (String) batchData.get("tenant");
        var jobId = (String) batchData.get("jobId");
        var fileName = (String) batchData.get("fileName");
        var totalRows = (int) batchData.get("totalRows");

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

            tryToUpdateImportStatus(fileName, jobId, totalRows, batches.size(), 0);
        }

    }

    private void tryToUpdateImportStatus(String fileName, String jobId, int totalRows, int successRow, int errorRows) {
        var importStatus = importStatusRepository.findByJobId(jobId);
        if (importStatus == null) {
            importStatus = ImportStatus.builder()
                    .jobId(jobId)
                    .totalRows(totalRows)
                    .build();
        }

        importStatus.setErrorRows(importStatus.getErrorRows() + errorRows);
        importStatus.setSuccessRows(importStatus.getSuccessRows() + successRow);

        importStatusRepository.save(importStatus);
    }
}
