package com.metechvn.contacts.events;

import com.metechvn.contacts.entities.Segmentation;
import org.springframework.context.ApplicationEvent;

public class SegmentationDeletedEvent extends ApplicationEvent {

    public SegmentationDeletedEvent(Segmentation segmentation) {
        super(segmentation);
    }
}
