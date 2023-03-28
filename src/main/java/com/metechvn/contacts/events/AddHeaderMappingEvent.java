package com.metechvn.contacts.events;

import com.metechvn.contacts.entities.ContactsFileEntity;
import org.springframework.context.ApplicationEvent;

public class AddHeaderMappingEvent extends ApplicationEvent {
    public AddHeaderMappingEvent(ContactsFileEntity contactsFileEntity) {
        super(contactsFileEntity);
    }
}


