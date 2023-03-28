package com.metechvn.contacts.eventlisteners;

import com.metechvn.contacts.entities.ContactsFileEntity;
import com.metechvn.contacts.events.AddHeaderMappingEvent;
import com.metechvn.contacts.repositories.ContactsFileEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalApplicationListenerAdapter;

import java.util.concurrent.ExecutionException;
@Component
public class AddHeaderMappingEventListener extends TransactionalApplicationListenerAdapter<AddHeaderMappingEvent> {
    public AddHeaderMappingEventListener(
            KafkaTemplate<String, Object> kafkaTemplate,  ContactsFileEntityRepository contactsFileEntityRepository) {
        super(new AddHeaderMappingEventHandler(kafkaTemplate, contactsFileEntityRepository));
    }

    static class AddHeaderMappingEventHandler implements ApplicationListener<AddHeaderMappingEvent> {

        private final Logger log = LoggerFactory.getLogger(this.getClass());
        private final KafkaTemplate<String, Object> kafkaTemplate;
        private final ContactsFileEntityRepository contactsFileEntityRepository;

        AddHeaderMappingEventHandler(KafkaTemplate<String, Object> kafkaTemplate, ContactsFileEntityRepository contactsFileEntityRepository) {
            this.kafkaTemplate = kafkaTemplate;
            this.contactsFileEntityRepository = contactsFileEntityRepository;

        }

        @Override
        public void onApplicationEvent(AddHeaderMappingEvent event) {
            if (!(event.getSource()!= null && event.getSource() instanceof ContactsFileEntity de)) return;
            var dynamicEntity = contactsFileEntityRepository.findById(de.getId());
            try {
                kafkaTemplate
                        .send("MKT.BE.HeaderMappingContacts", dynamicEntity)
                        .get();

                log.debug("Sent entity {} created to kafka", de.getId());
            } catch (InterruptedException | ExecutionException e) {
                log.error("Cannot send message to kafka. Trace {}", e.getMessage());
            }
        }
    }

}
