package com.metechvn.dynamic.events;

import org.springframework.context.ApplicationEvent;

import java.util.Collection;

public class DynamicEntitySavedEvent extends ApplicationEvent {

    public DynamicEntitySavedEvent(Collection<Object> entities) {
        super(entities);
    }
}
