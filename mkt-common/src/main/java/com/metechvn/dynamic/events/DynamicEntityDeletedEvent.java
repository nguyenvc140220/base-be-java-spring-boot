package com.metechvn.dynamic.events;

import org.springframework.context.ApplicationEvent;

import java.util.Collection;

public class DynamicEntityDeletedEvent extends ApplicationEvent {

    public DynamicEntityDeletedEvent(Collection<Object> entities) {
        super(entities);
    }
}
