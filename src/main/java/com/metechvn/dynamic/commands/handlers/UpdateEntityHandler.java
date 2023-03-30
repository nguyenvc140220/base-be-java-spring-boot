package com.metechvn.dynamic.commands.handlers;


import com.metechvn.common.CurrentTenantProvider;
import com.metechvn.dynamic.commands.UpdateEntityCommand;
import com.metechvn.dynamic.entities.DynamicEntity;
import com.metechvn.dynamic.repositories.DynamicEntityRepository;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import com.metechvn.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateEntityHandler implements RequestHandler<DynamicEntity, UpdateEntityCommand> {
    private final CurrentTenantProvider currentTenantProvider;
    private final DynamicEntityRepository dynamicEntityRepository;
    private final DynamicEntityTypeRepository dynamicEntityTypeRepository;
    @Override
    public DynamicEntity handle(UpdateEntityCommand cmd) {
        var typeIncludeProps = dynamicEntityTypeRepository.findIncludeRelationsByCode(cmd.getCode());
        var entity = dynamicEntityRepository.findById(cmd.getIdEntity());
        if (typeIncludeProps == null && entity == null)
            throw new BusinessException(String.format("Cannot find object named '%s' or entityID '$s'", cmd.getCode(), cmd.getIdEntity()));

        var entityGet= entity.get();
        entityGet.setEntityType(typeIncludeProps);
        entityGet.setTenant(currentTenantProvider.getTenant());

        for (var entry : cmd.getProperties().entrySet()) {
            if (!typeIncludeProps.exists(entry.getKey())) continue;

            // validate value
            //...

            // set value
            entityGet.set(typeIncludeProps.getProperty(entry.getKey()), entry.getValue());
        }

        return dynamicEntityRepository.save(entityGet);
    }
}
