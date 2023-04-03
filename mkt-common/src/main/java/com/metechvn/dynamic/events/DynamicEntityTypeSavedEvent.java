package com.metechvn.dynamic.events;

import com.metechvn.dynamic.entities.DynamicEntityType;
import org.springframework.context.ApplicationEvent;

public class DynamicEntityTypeSavedEvent extends ApplicationEvent {
    public DynamicEntityTypeSavedEvent(DynamicEntityType dynamicEntityType) {
        super(dynamicEntityType);
    }
}
