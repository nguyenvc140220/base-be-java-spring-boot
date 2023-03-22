package com.metechvn.dynamic.eventlisteners;

import com.metechvn.dynamic.dtos.DynamicPropertyDto;
import com.metechvn.dynamic.entities.DynamicEntityType;
import com.metechvn.dynamic.etos.DynamicEntityTypeSavedEto;
import com.metechvn.dynamic.events.DynamicEntityTypeSavedEvent;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalApplicationListenerAdapter;

import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class DynamicEntityTypeSavedEventListener
        extends TransactionalApplicationListenerAdapter<DynamicEntityTypeSavedEvent> {

    public DynamicEntityTypeSavedEventListener(
            KafkaTemplate<String, Object> kafkaTemplate, DynamicEntityTypeRepository dynamicEntityTypeRepository) {
        super(new DynamicEntityTypeSavedEventHandler(kafkaTemplate, dynamicEntityTypeRepository));
    }

    static class DynamicEntityTypeSavedEventHandler implements ApplicationListener<DynamicEntityTypeSavedEvent> {

        private final Logger log = LoggerFactory.getLogger(this.getClass());

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

            var properties = typeIncludeRelations.getProperties()
                    .values()
                    .stream()
                    .map(p -> {
                        var dataType = p.getProperty().getDataType();
                        var property = DynamicPropertyDto.builder().code(p.getCode()).dataType(dataType).build();
                        property.setId(p.getId());

                        return property;
                    })
                    .collect(Collectors.toMap(DynamicPropertyDto::getCode, p -> p));

            var entityTypeEto = DynamicEntityTypeSavedEto
                    .builder()
                    .code(typeIncludeRelations.getCode())
                    .properties(properties)
                    .build();

            entityTypeEto.setId(typeIncludeRelations.getId());
            entityTypeEto.setTenant(typeIncludeRelations.getTenant());

            try {
                kafkaTemplate
                        .send("MKT.BE.DynamicEntityTypeSaved", entityTypeEto.getId().toString(), entityTypeEto)
                        .get();

                log.debug("Sent entity type {} created to kafka", entityTypeEto.getId());
            } catch (InterruptedException | ExecutionException e) {
                log.error("Cannot send message to kafka. Trace {}", e.getMessage());
            }
        }
    }
}
