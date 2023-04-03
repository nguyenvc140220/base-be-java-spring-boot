package com.metechvn.dynamic.events;

import com.metechvn.dynamic.entities.DynamicEntityTypeProperty;
import org.springframework.context.ApplicationEvent;

public class DynamicEntityTypePropertyDeletedEvent extends ApplicationEvent {
    public DynamicEntityTypePropertyDeletedEvent(DynamicEntityTypeProperty source) {
        super(source);
    }
}
