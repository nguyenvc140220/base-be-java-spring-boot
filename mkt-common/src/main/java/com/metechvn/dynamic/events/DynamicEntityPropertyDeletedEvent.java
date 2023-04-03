package com.metechvn.dynamic.events;

import com.metechvn.dynamic.entities.DynamicEntityProperty;
import org.springframework.context.ApplicationEvent;

public class DynamicEntityPropertyDeletedEvent extends ApplicationEvent {
    public DynamicEntityPropertyDeletedEvent(DynamicEntityProperty source) {
        super(source);
    }
}
