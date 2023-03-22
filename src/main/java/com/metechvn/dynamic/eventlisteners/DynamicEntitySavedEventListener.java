package com.metechvn.dynamic.eventlisteners;

import com.metechvn.dynamic.entities.DynamicEntity;
import com.metechvn.dynamic.events.DynamicEntitySavedEvent;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalApplicationListenerAdapter;

@Component
public class DynamicEntitySavedEventListener
        extends TransactionalApplicationListenerAdapter<DynamicEntitySavedEvent> {

    public DynamicEntitySavedEventListener(
            KafkaTemplate<String, Object> kafkaTemplate, DynamicEntityTypeRepository dynamicEntityTypeRepository) {
        super(new DynamicEntitySavedEventHandler(kafkaTemplate));
    }

    static class DynamicEntitySavedEventHandler implements ApplicationListener<DynamicEntitySavedEvent> {

        private final KafkaTemplate<String, Object> kafkaTemplate;

        DynamicEntitySavedEventHandler(KafkaTemplate<String, Object> kafkaTemplate) {
            this.kafkaTemplate = kafkaTemplate;
        }

        @Override
        public void onApplicationEvent(DynamicEntitySavedEvent event) {
            if (!(event.getSource() != null && event.getSource() instanceof DynamicEntity de)) return;


        }
    }
}
