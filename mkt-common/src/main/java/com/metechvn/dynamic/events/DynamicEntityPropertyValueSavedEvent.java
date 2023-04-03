package com.metechvn.dynamic.events;

import com.metechvn.dynamic.entities.DynamicEntityProperty;
import com.metechvn.dynamic.entities.DynamicEntityPropertyValue;
import org.springframework.context.ApplicationEvent;

public class DynamicEntityPropertyValueSavedEvent extends ApplicationEvent {
    public DynamicEntityPropertyValueSavedEvent(DynamicEntityPropertyValue source) {
        super(source);
    }
}
