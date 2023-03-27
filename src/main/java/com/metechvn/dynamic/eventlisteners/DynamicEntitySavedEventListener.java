package com.metechvn.dynamic.eventlisteners;

import com.metechvn.dynamic.dtos.DynamicEntityPropertyDto;
import com.metechvn.dynamic.dtos.DynamicEntityTypeDto;
import com.metechvn.dynamic.dtos.DynamicPropertyDto;
import com.metechvn.dynamic.entities.DynamicEntity;
import com.metechvn.dynamic.etos.DynamicEntitySavedEto;
import com.metechvn.dynamic.events.DynamicEntitySavedEvent;
import com.metechvn.dynamic.repositories.DynamicEntityRepository;
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
public class DynamicEntitySavedEventListener
        extends TransactionalApplicationListenerAdapter<DynamicEntitySavedEvent> {

    public DynamicEntitySavedEventListener(
            KafkaTemplate<String, Object> kafkaTemplate, DynamicEntityTypeRepository dynamicEntityTypeRepository, DynamicEntityRepository dynamicEntityRepository) {
        super(new DynamicEntitySavedEventHandler(kafkaTemplate, dynamicEntityTypeRepository, dynamicEntityRepository));
    }

    static class DynamicEntitySavedEventHandler implements ApplicationListener<DynamicEntitySavedEvent> {

        private final Logger log = LoggerFactory.getLogger(this.getClass());
        private final KafkaTemplate<String, Object> kafkaTemplate;
        private final DynamicEntityTypeRepository dynamicEntityTypeRepository;
        private final DynamicEntityRepository dynamicEntityRepository;

        DynamicEntitySavedEventHandler(KafkaTemplate<String, Object> kafkaTemplate, DynamicEntityTypeRepository dynamicEntityTypeRepository, DynamicEntityRepository dynamicEntityRepository) {
            this.kafkaTemplate = kafkaTemplate;
            this.dynamicEntityTypeRepository = dynamicEntityTypeRepository;
            this.dynamicEntityRepository = dynamicEntityRepository;
        }

        @Override
        public void onApplicationEvent(DynamicEntitySavedEvent event) {
            if (!(event.getSource() != null && event.getSource() instanceof DynamicEntity de)) return;

            var dynamicEntity = dynamicEntityRepository.findIncludeRelationsById(de.getId());
            if (dynamicEntity == null) return;
            var dynamicEntityType = dynamicEntity.getEntityType();
            var dynamicEntityTypeDto = DynamicEntityTypeDto
                    .builder()
                    .code(dynamicEntityType.getCode())
                    .description(dynamicEntityType.getDescription())
                    .displayName(dynamicEntityType.getDisplayName())
                    .build();
            dynamicEntityTypeDto.setId(dynamicEntityType.getId());
            dynamicEntityTypeDto.setTenant(dynamicEntityType.getTenant());

            var properties = dynamicEntity.getProperties()
                    .values()
                    .stream()
                    .map(p -> {
                        var property = DynamicEntityPropertyDto
                                .builder()
                                .propertyCode(p.getEntityProperty().getCode())
                                .entityPropertyValue(p.getEntityPropertyValue().getValue())
                                .build();
                        property.setId(p.getId());
                        property.setTenant(p.getTenant());

                        return property;
                    })
                    .collect(Collectors.toMap(DynamicEntityPropertyDto::getPropertyCode, p -> p));


            var entityEto = DynamicEntitySavedEto
                    .builder()
                    .entityType(dynamicEntityTypeDto)
                    .properties(properties)
                    .build();
            entityEto.setId(dynamicEntity.getId());
            entityEto.setTenant(dynamicEntity.getTenant());
            try {
                kafkaTemplate
                        .send("MKT.BE.DynamicEntitySaved", de.getId().toString(), entityEto)
                        .get();

                log.debug("Sent entity {} created to kafka", de.getId());
            } catch (InterruptedException | ExecutionException e) {
                log.error("Cannot send message to kafka. Trace {}", e.getMessage());
            }
        }
    }
}
