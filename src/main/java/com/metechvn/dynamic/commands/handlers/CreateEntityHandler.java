package com.metechvn.dynamic.commands.handlers;

import com.metechvn.dynamic.commands.CreateEntityCommand;
import com.metechvn.dynamic.entities.DynamicEntity;
import com.metechvn.dynamic.repositories.DynamicEntityRepository;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import com.metechvn.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateEntityHandler implements RequestHandler<DynamicEntity, CreateEntityCommand> {

    private final DynamicEntityRepository dynamicEntityRepository;
    private final DynamicEntityTypeRepository dynamicEntityTypeRepository;


    @Override
    public DynamicEntity handle(CreateEntityCommand cmd) {
        var typeIncludeProps = dynamicEntityTypeRepository.findIncludeRelationsByCode(cmd.getCode());
        if (typeIncludeProps == null)
            throw new BusinessException(String.format("Cannot find object named '%s'", cmd.getCode()));

        var entity = new DynamicEntity();
        entity.setEntityType(typeIncludeProps);

        for (var entry : cmd.getProperties().entrySet()) {
            if (!typeIncludeProps.exists(entry.getKey())) continue;

            // validate value
            //...

            // set value
            entity.set(typeIncludeProps.getProperty(entry.getKey()), entry.getValue());
        }

        return dynamicEntityRepository.save(entity);
    }
}
