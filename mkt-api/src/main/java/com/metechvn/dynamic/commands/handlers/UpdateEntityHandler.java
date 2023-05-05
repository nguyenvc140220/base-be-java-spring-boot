package com.metechvn.dynamic.commands.handlers;


import com.metechvn.common.CurrentTenantProvider;
import com.metechvn.dynamic.commands.UpdateEntityCommand;
import com.metechvn.dynamic.entities.DynamicEntity;
import com.metechvn.dynamic.repositories.DynamicEntityRepository;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import com.metechvn.exception.BusinessException;
import com.metechvn.validators.IDynamicTypeValidator;
import com.metechvn.validators.dtos.DynamicTypeValidator;
import com.metechvn.validators.dtos.DynamicTypeValidatorDto;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UpdateEntityHandler implements RequestHandler<DynamicEntity, UpdateEntityCommand> {
    private final IDynamicTypeValidator validator;
    private final CurrentTenantProvider currentTenantProvider;
    private final DynamicEntityRepository dynamicEntityRepository;
    private final DynamicEntityTypeRepository dynamicEntityTypeRepository;
    @Override
    public DynamicEntity handle(UpdateEntityCommand cmd) {
        var typeIncludeProps = dynamicEntityTypeRepository.findIncludeRelationsByCode(cmd.getCode());
        var currentEntityOptional = dynamicEntityRepository.findById(cmd.getIdEntity());
        if (typeIncludeProps == null || currentEntityOptional.isEmpty())
            throw new BusinessException(String.format("Cannot find object named '%s' or entityID '%s'", cmd.getCode(), cmd.getIdEntity()));

        var currentEntity = currentEntityOptional.get();

        var updatedEntity = new DynamicEntity();
        updatedEntity.setEntityType(currentEntity.getEntityType());
        updatedEntity.setId(currentEntity.getId());
        updatedEntity.setTenant(currentEntity.getTenant());;

        var validators = typeIncludeProps.getProperties().entrySet()
                .stream()
                .filter(e -> StringUtils.hasText(e.getValue().getProperty().getValidators()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> DynamicTypeValidator.fromJson(e.getValue().getProperty().getValidators())
                ));

        for (var entry : cmd.getProperties().entrySet()) {
            if (!typeIncludeProps.exists(entry.getKey())) continue;
            var propValidators = validators.get(entry.getKey());
            if (propValidators != null && !propValidators.isEmpty()) {
                var validator = new DynamicTypeValidatorDto(entry.getKey(), entry.getValue(), propValidators);
                validator.setValidators(propValidators);
                this.validator.test(validator);
            }
            var property = currentEntity.getProperties().get(entry.getKey());
            if (property != null){

                var checkValue = currentEntity
                        .getProperties()
                        .get(entry.getKey())
                        .getEntityPropertyValue()
                        .getValue();
                if (checkValue == null){
                    updatedEntity.set(typeIncludeProps.getProperty(entry.getKey()), entry.getValue());
                } else if (!checkValue.equals(entry.getValue())){
                    updatedEntity.set(typeIncludeProps.getProperty(entry.getKey()), entry.getValue());
                }
            } else updatedEntity.set(typeIncludeProps.getProperty(entry.getKey()), entry.getValue());
        }
        return dynamicEntityRepository.save(updatedEntity);
    }
}
