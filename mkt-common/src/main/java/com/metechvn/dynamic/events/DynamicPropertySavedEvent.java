package com.metechvn.dynamic.events;

import com.metechvn.dynamic.entities.DynamicProperty;
import org.springframework.context.ApplicationEvent;

public class DynamicPropertySavedEvent extends ApplicationEvent {

    public DynamicPropertySavedEvent(DynamicProperty source) {
        super(source);
    }

}
