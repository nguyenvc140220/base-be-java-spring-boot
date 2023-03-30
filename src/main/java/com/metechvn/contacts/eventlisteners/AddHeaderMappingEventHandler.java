package com.metechvn.contacts.eventlisteners;

import com.metechvn.contacts.commands.AddHeaderMappingCommand;
import com.metechvn.contacts.events.AddHeaderMappingEvent;
import com.metechvn.contacts.repositories.ContactsFileEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
@Component
public  class AddHeaderMappingEventHandler implements ApplicationListener<AddHeaderMappingEvent> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ContactsFileEntityRepository contactsFileEntityRepository;

    AddHeaderMappingEventHandler(KafkaTemplate<String, Object> kafkaTemplate, ContactsFileEntityRepository contactsFileEntityRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.contactsFileEntityRepository = contactsFileEntityRepository;
    }

    @Override
    public void onApplicationEvent(AddHeaderMappingEvent event) {
        if (!(event.getSource()!= null && event.getSource() instanceof AddHeaderMappingCommand de)) return;
        try {
            kafkaTemplate
                    .send("MKT.BE.ImportExcelRequest", de.getHeaders())
                    .get();
            log.debug("Sent entity {} created to kafka");
        } catch (InterruptedException | ExecutionException e) {
            log.error("Cannot send message to kafka. Trace {}", e.getMessage());
        }
    }
}