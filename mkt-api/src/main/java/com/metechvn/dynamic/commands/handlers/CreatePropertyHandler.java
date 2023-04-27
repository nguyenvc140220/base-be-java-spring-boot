package com.metechvn.dynamic.commands.handlers;

import com.metechvn.common.CurrentTenantProvider;
import com.metechvn.dynamic.commands.CreatePropertyCommand;
import com.metechvn.dynamic.entities.DynamicProperty;
import com.metechvn.dynamic.repositories.DynamicPropertyRepository;
import com.metechvn.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePropertyHandler implements RequestHandler<DynamicProperty, CreatePropertyCommand> {

    private final CurrentTenantProvider currentTenantProvider;
    private final DynamicPropertyRepository dynamicPropertyRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public DynamicProperty handle(@NotNull CreatePropertyCommand cmd) {
        var existing = dynamicPropertyRepository.findByCode(cmd.getCode());
        if (existing != null)
            throw new BusinessException(String.format("Mã %s đã tồn tại", cmd.getCode()));

        var dynamicEntityType = DynamicProperty.builder()
                .code(cmd.getCode())
                .displayName(cmd.getDisplayName())
                .dataType(cmd.getDataType())
                .inputType(cmd.getInputType())
                .validators(cmd.getValidators())
                .defaultValue(cmd.getDefaultValue())
                .hintText(cmd.getHintText())
                .tooltip(cmd.getTooltip())
                .configurable(cmd.getConfigurationable())
                .editable(cmd.getEditable())
                .removable(true)
                .build();
        dynamicEntityType.setTenant(currentTenantProvider.getTenant());
        return dynamicPropertyRepository.save(dynamicEntityType);
    }
}
