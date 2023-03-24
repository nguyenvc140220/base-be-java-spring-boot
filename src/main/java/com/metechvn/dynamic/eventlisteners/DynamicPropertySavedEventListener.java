package com.metechvn.dynamic.eventlisteners;

import com.metechvn.dynamic.entities.DynamicProperty;
import com.metechvn.dynamic.events.DynamicPropertySavedEvent;
import com.metechvn.dynamic.repositories.DynamicPropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalApplicationListenerAdapter;

@Component
public class DynamicPropertySavedEventListener
        extends TransactionalApplicationListenerAdapter<DynamicPropertySavedEvent> {

    public DynamicPropertySavedEventListener(
            KafkaTemplate<String, Object> kafkaTemplate, DynamicPropertyRepository dynamicPropertyRepository) {
        super(new DynamicPropertySavedEventHandler(kafkaTemplate, dynamicPropertyRepository));
    }

    static class DynamicPropertySavedEventHandler implements ApplicationListener<DynamicPropertySavedEvent> {

        private final Logger log = LoggerFactory.getLogger(this.getClass());

        private final KafkaTemplate<String, Object> kafkaTemplate;
        private final DynamicPropertyRepository dynamicPropertyRepository;

        DynamicPropertySavedEventHandler(
                KafkaTemplate<String, Object> kafkaTemplate,
                DynamicPropertyRepository dynamicPropertyRepository) {
            this.kafkaTemplate = kafkaTemplate;
            this.dynamicPropertyRepository = dynamicPropertyRepository;
        }

        @Override
        public void onApplicationEvent(DynamicPropertySavedEvent event) {
            if (!(event.getSource() != null && event.getSource() instanceof DynamicProperty dt)) return;
        }
    }
}
