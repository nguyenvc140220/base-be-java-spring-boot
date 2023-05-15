package com.metechvn.dynamic.processors;

import com.metechvn.dynamic.dtos.BatchProcessResult;
import com.metechvn.dynamic.entities.DynamicEntity;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import com.metechvn.validators.IDynamicTypeValidator;
import com.metechvn.validators.dtos.DynamicTypeValidator;
import com.metechvn.validators.dtos.DynamicTypeValidatorDto;
import com.metechvn.validators.exceptions.DynamicTypeValidatorException;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DynamicEntityBatchProcessor {

    private final IDynamicTypeValidator dynamicTypeValidator;
    private final DynamicEntityTypeRepository entityTypeRepository;
    private final EntityManagerFactory emf;
//    private final CurrentTenantProvider currentTenantProvider;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public BatchProcessResult process(String tenant, String entityCode, Collection<Map<String, Object>> batches) {
        return this.process(UUID.randomUUID().toString(), tenant, entityCode, batches);
    }

    public BatchProcessResult process(
            String correlationId, String tenant, String entityCode, Collection<Map<String, Object>> batches) {
        if (StringUtils.isEmpty(tenant) || StringUtils.isEmpty(entityCode) || batches == null || batches.isEmpty()) {
            log.error("{} Received invalid batch tenant {} entity type {}", correlationId, tenant, entityCode);
            return BatchProcessResult.empty();
        }

        var result = new BatchProcessResult(tenant, entityCode);

/*        currentTenantProvider.setTenant(tenant);*/
        var entityType = entityTypeRepository.findIncludeRelationsByCode(entityCode);
        if (entityType == null) {
            log.error("{} Cannot find entity type with code {} in tenant {}", correlationId, entityCode, tenant);
            return result;
        }


        var sessionFactory = emf.unwrap(SessionFactory.class);
        try (var session = sessionFactory.openSession()) {
            Transaction transaction;
            try {
                transaction = session.beginTransaction();
            } catch (Exception e) {
                transaction = session.getTransaction();
            }

            var validators = entityType.getProperties().entrySet()
                    .stream()
                    .filter(e -> org.springframework.util.StringUtils.hasText(e.getValue().getProperty().getValidators()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> DynamicTypeValidator.fromJson(e.getValue().getProperty().getValidators())
                    ));

            for (var row : batches) {
                var entity = new DynamicEntity();
                entity.setEntityType(entityType);
                entity.setTenant(tenant);

                var validationResult = true;
                for (var entry : row.entrySet()) {
                    if (!entityType.exists(entry.getKey())) continue;

                    try {
                        dynamicTypeValidator.test(new DynamicTypeValidatorDto(
                                entry.getKey(),
                                entry.getValue(),
                                validators.get(entry.getKey())
                        ));
                    } catch (DynamicTypeValidatorException e) {
                        validationResult = false;
                        continue;
                    }
                    if (validationResult) {
                        entity.set(entityType.getProperty(entry.getKey()), entry.getValue());
                    }
                }

                session.persist(entity);
                result.success(row);
            }

            transaction.commit();
        }

        return result;
    }
}
