package com.metechvn.dynamic.events;

import com.metechvn.dynamic.entities.DynamicEntityProperty;
import org.springframework.context.ApplicationEvent;

public class DynamicEntityPropertySavedEvent extends ApplicationEvent {
    public DynamicEntityPropertySavedEvent(DynamicEntityProperty source) {
        super(source);
    }
}
