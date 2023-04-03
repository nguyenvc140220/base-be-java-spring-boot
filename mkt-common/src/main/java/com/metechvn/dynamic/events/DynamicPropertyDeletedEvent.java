package com.metechvn.dynamic.events;

import com.metechvn.dynamic.entities.DynamicProperty;
import org.springframework.context.ApplicationEvent;

public class DynamicPropertyDeletedEvent extends ApplicationEvent {

    public DynamicPropertyDeletedEvent(DynamicProperty source) {
        super(source);
    }

}
