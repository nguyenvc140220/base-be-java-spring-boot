package com.metechvn.contacts.events;

import com.metechvn.resource.entities.ImportFile;
import org.springframework.context.ApplicationEvent;

public class ContactUploadEvent extends ApplicationEvent {

    public ContactUploadEvent(ImportFile importFile) {
        super(importFile);
    }
}
