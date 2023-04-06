package com.metechvn.dynamic.events;

import com.metechvn.dynamic.etos.ImportBatchProcessEto;
import org.springframework.context.ApplicationEvent;

public class BatchImportedEvent extends ApplicationEvent {
    public BatchImportedEvent(ImportBatchProcessEto source) {
        super(source);
    }
}
