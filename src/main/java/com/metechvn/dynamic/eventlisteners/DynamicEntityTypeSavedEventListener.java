package com.metechvn.dynamic.eventlisteners;

import com.metechvn.dynamic.entities.DynamicEntityType;
import com.metechvn.dynamic.events.DynamicEntityTypeSavedEvent;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalApplicationListenerAdapter;

@Component
public class DynamicEntityTypeSavedEventListener
        extends TransactionalApplicationListenerAdapter<DynamicEntityTypeSavedEvent> {

    public DynamicEntityTypeSavedEventListener(
            KafkaTemplate<String, Object> kafkaTemplate, DynamicEntityTypeRepository dynamicEntityTypeRepository) {
        super(new DynamicEntityTypeSavedEventHandler(kafkaTemplate, dynamicEntityTypeRepository));
    }

    static class DynamicEntityTypeSavedEventHandler implements ApplicationListener<DynamicEntityTypeSavedEvent> {

        private final KafkaTemplate<String, Object> kafkaTemplate;
        private final DynamicEntityTypeRepository dynamicEntityTypeRepository;

        DynamicEntityTypeSavedEventHandler(
                KafkaTemplate<String, Object> kafkaTemplate,
                DynamicEntityTypeRepository dynamicEntityTypeRepository) {
            this.kafkaTemplate = kafkaTemplate;
            this.dynamicEntityTypeRepository = dynamicEntityTypeRepository;
        }

        @Override
        public void onApplicationEvent(DynamicEntityTypeSavedEvent event) {
            if (!(event.getSource() != null && event.getSource() instanceof DynamicEntityType dt)) return;

            var typeIncludeRelations = dynamicEntityTypeRepository.findIncludeRelationsById(dt.getId());
            if (typeIncludeRelations == null) return;

            System.out.println(typeIncludeRelations);
        }
    }
}
