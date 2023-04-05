package com.metechvn.contacts.eventlisteners;

import com.metechvn.contacts.entities.Segmentation;
import com.metechvn.contacts.events.SegmentationDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SegmentationDeletedEventHandler implements ApplicationListener<SegmentationDeletedEvent> {

    @Override
    public void onApplicationEvent(SegmentationDeletedEvent event) {
        if (event.getSource() == null || !(event.getSource() instanceof Segmentation seg)) {
            log.warn(
                    "{} triggered without source of source not instance of {}",
                    SegmentationDeletedEvent.class.getName(),
                    Segmentation.class.getName()
            );
            return;
        }

        // TODO: remove segmentation(s) of campaigns which assigned

        var filterCount = seg.getFilters() == null ? 0 : seg.getFilters().size();

        log.info("Segmentation with id {} deleted but not include {} filter(s)", seg.getId(), filterCount);
    }
}
