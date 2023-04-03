package com.metechvn.dynamic.events;

import com.metechvn.dynamic.entities.DynamicEntityType;
import org.springframework.context.ApplicationEvent;

public class DynamicEntityTypeDeletedEvent extends ApplicationEvent {
    public DynamicEntityTypeDeletedEvent(DynamicEntityType dynamicEntityType) {
        super(dynamicEntityType);
    }
}
