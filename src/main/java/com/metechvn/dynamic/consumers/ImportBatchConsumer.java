package com.metechvn.dynamic.consumers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metechvn.dynamic.entities.DynamicEntity;
import com.metechvn.resource.entities.ImportStatus;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import com.metechvn.resource.repositories.ImportStatusRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class ImportBatchConsumer {

    private final ObjectMapper objectMapper;
    private final ImportStatusRepository importStatusRepository;
    private final DynamicEntityTypeRepository entityTypeRepository;
    private final EntityManagerFactory emf;
    private final ExecutorService executorService;

    public ImportBatchConsumer(
            ObjectMapper objectMapper,
            ImportStatusRepository importStatusRepository,
            DynamicEntityTypeRepository entityTypeRepository,
            EntityManagerFactory emf,
            @Value("${import.max-threads:16}") int numOfThreads) {
        this.objectMapper = objectMapper;
        this.importStatusRepository = importStatusRepository;
        this.entityTypeRepository = entityTypeRepository;
        this.emf = emf;
        this.executorService = Executors.newFixedThreadPool(numOfThreads);
    }

    @KafkaListener(
            topics = "MKT.JOB.ImportExcelBatch",
            groupId = "${spring.kafka.client-id}",
            containerFactory = "objListenerContainerFactory")
    public void onBatch(ConsumerRecord<Object, Map<String, Object>> cr) {
        if (cr.value() == null) {
            return;
        }

        executorService.submit(this.importThread(cr.value()));

        try {
            var activeThreads = ((ThreadPoolExecutor) executorService).getActiveCount();
            log.info("Submit new import thread. Current active {}", activeThreads);
        } catch (Exception ignored) {
        }
    }

    private void tryToUpdateImportStatus(String fileName, String jobId, int totalRows, int successRow, int errorRows) {
        try {
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
        } catch (Exception e) {
            log.error("Cannot update import status job {} file {}. Trace {}", jobId, fileName, e.getMessage());
        }
    }

    private Runnable importThread(Map<String, Object> batchData) {
        return () -> {
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
        };
    }
}
