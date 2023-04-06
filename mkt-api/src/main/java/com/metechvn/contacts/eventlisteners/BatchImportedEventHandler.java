package com.metechvn.contacts.eventlisteners;

import com.metechvn.contacts.repositories.SegmentationRepository;
import com.metechvn.dynamic.etos.ImportBatchProcessEto;
import com.metechvn.dynamic.events.BatchImportedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchImportedEventHandler implements ApplicationListener<BatchImportedEvent> {

    private final SegmentationRepository segmentationRepository;

    @Override
    public void onApplicationEvent(BatchImportedEvent event) {
        if (event.getSource() == null
                || !(event.getSource() instanceof ImportBatchProcessEto process)
                || !StringUtils.hasText(process.getFileName())) {
            log.warn(
                    "Event {} triggered with invalid source! Required {}",
                    BatchImportedEvent.class.getName(),
                    ImportBatchProcessEto.class.getName()
            );
            return;
        }

        var segmentations = segmentationRepository.findByName(process.getFileName());
        if (segmentations == null || segmentations.isEmpty()) {
            log.warn(
                    "Cannot found any segmentation(s) named {}. Event {}",
                    process.getFileName(),
                    BatchImportedEvent.class.getName()
            );
            return;
        }

        for (var segmentation : segmentations) {
            segmentation.incContacts(process.getSuccessRows());
            log.debug(
                    "Segmentation {} increase contacts to {}",
                    segmentation.getId(),
                    segmentation.getNumOfContacts()
            );
        }

        segmentationRepository.saveAll(segmentations);
    }
}
