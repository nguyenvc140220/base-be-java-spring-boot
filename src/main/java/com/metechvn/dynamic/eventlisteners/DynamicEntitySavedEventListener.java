package com.metechvn.dynamic.eventlisteners;

import com.metechvn.dynamic.dtos.BatchDynamicEntityDto;
import com.metechvn.dynamic.dtos.FlattenDynamicEntityDto;
import com.metechvn.dynamic.entities.DynamicEntity;
import com.metechvn.dynamic.events.DynamicEntitySavedEvent;
import com.metechvn.dynamic.repositories.DynamicEntityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalApplicationListenerAdapter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
public class DynamicEntitySavedEventListener
        extends TransactionalApplicationListenerAdapter<DynamicEntitySavedEvent> {

    public DynamicEntitySavedEventListener(DynamicEntitySavedEventHandler eventHandler) {
        super(eventHandler);
    }

    @Component
    @RequiredArgsConstructor
    static class DynamicEntitySavedEventHandler implements ApplicationListener<DynamicEntitySavedEvent> {

        private final Logger log = LoggerFactory.getLogger(this.getClass());
        private final KafkaTemplate<String, Object> kafkaTemplate;
        private final DynamicEntityRepository dynamicEntityRepository;

        @Override
        public void onApplicationEvent(DynamicEntitySavedEvent event) {
            if (event.getSource() == null) {
                return;
            }

            var entities = new ArrayList<DynamicEntity>();

            if (event.getSource() instanceof DynamicEntity de) entities.add(de);
            else if (event.getSource() instanceof List<?> deLst) {
                entities.addAll(
                        deLst.stream().filter(d -> d instanceof DynamicEntity).map(x -> (DynamicEntity) x).toList()
                );
            }

            var entityIds = entities.stream().map(DynamicEntity::getId).toArray(UUID[]::new);
            var dynamicEntities = dynamicEntityRepository.findIncludeRelationsById(entityIds);
            if (dynamicEntities == null || dynamicEntities.isEmpty()) {
                return;
            }

            var tenantEntities = new HashMap<String, BatchDynamicEntityDto<UUID>>();
            for (var entity : dynamicEntities) {
                if (!StringUtils.hasText(entity.getTenant())) continue;

                if (!tenantEntities.containsKey(entity.getTenant())) {
                    tenantEntities.put(entity.getTenant(), new BatchDynamicEntityDto<>(entity.getTenant()));
                }

                var flattenEntity = new FlattenDynamicEntityDto<>(entity.getId());
                for (var prop : entity.getProperties().values()) {
                    flattenEntity.put(prop.getCode(), prop.getEntityPropertyValue().getValue());
                }

                tenantEntities.get(entity.getTenant()).add(flattenEntity);
            }

            for (var entry : tenantEntities.entrySet()) {
                try {
                    kafkaTemplate.send("MKT.BE.DynamicEntitySaved", entry.getValue()).get();

                    log.debug("Sent {} entity(s) of tenant {} to kafka", entry.getValue().batchSize(), entry.getKey());
                } catch (InterruptedException | ExecutionException e) {
                    log.error("Cannot send message to kafka. Trace {}", e.getMessage());
                }
            }
        }
    }
}
