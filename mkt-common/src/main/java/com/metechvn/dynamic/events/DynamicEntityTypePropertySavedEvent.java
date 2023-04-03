package com.metechvn.dynamic.events;

import com.metechvn.dynamic.entities.DynamicEntityTypeProperty;
import org.springframework.context.ApplicationEvent;

public class DynamicEntityTypePropertySavedEvent extends ApplicationEvent {
    public DynamicEntityTypePropertySavedEvent(DynamicEntityTypeProperty source) {
        super(source);
    }
}
