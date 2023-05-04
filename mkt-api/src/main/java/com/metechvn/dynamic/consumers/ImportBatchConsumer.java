package com.metechvn.dynamic.consumers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metechvn.dynamic.etos.ImportBatchProcessEto;
import com.metechvn.dynamic.events.BatchImportedEvent;
import com.metechvn.dynamic.processors.DynamicEntityBatchProcessor;
import com.metechvn.resource.repositories.ImportFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImportBatchConsumer {

    private final ObjectMapper objectMapper;
    private final ApplicationEventPublisher publisher;
    private final DynamicEntityBatchProcessor processor;
    private final ImportFileRepository importFileRepository;

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

            if (importFile.getImportStatus().getTotalRows() <= 0) importFile.getImportStatus().setTotalRows(totalRows);

            importFile.getImportStatus().incError(errorRows);
            importFile.getImportStatus().incSuccess(successRow);

            importFileRepository.save(importFile);

            publisher.publishEvent(new BatchImportedEvent(
                    ImportBatchProcessEto.builder()
                            .fileName(importFile.getFileName())
                            .errorRows(errorRows)
                            .successRows(successRow)
                            .totalRows(totalRows)
                            .build()
            ));
        } catch (Exception e) {
            log.error("Cannot update import status job {} file {}. Trace {}", jobId, fileName, e.getMessage());
        }
    }

    private Runnable importThread(Map<String, Object> batchData) {
        return () -> {
            var tenant = (String) batchData.get("tenant");
            var jobId = (String) batchData.get("jobId");
            var fileName = (String) batchData.get("fileName");
            var entityCode = (String) batchData.get("entityType");

            var totalRows = 0;
            if (batchData.get("totalRows") instanceof Integer rows) totalRows = rows;

            var batches = objectMapper.convertValue(
                    batchData.get("batches"),
                    new TypeReference<List<Map<String, Object>>>() {
                    }
            );

            var result = processor.process(jobId, tenant, entityCode, batches);

            tryToUpdateImportStatus(fileName, jobId, totalRows, result.success(), result.error());

            // TODO: push error rows to kafka to send end-user
        };
    }
}
