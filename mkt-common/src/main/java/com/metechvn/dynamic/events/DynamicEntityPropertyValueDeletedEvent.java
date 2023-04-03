package com.metechvn.dynamic.events;

import com.metechvn.dynamic.entities.DynamicEntityPropertyValue;
import org.springframework.context.ApplicationEvent;

public class DynamicEntityPropertyValueDeletedEvent extends ApplicationEvent {
    public DynamicEntityPropertyValueDeletedEvent(DynamicEntityPropertyValue source) {
        super(source);
    }
}
