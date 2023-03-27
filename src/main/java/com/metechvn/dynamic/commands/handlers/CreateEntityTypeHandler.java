package com.metechvn.dynamic.commands.handlers;

import com.metechvn.common.CurrentTenantProvider;
import com.metechvn.dynamic.commands.CreateEntityTypeCommand;
import com.metechvn.dynamic.entities.DynamicEntityType;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import com.metechvn.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateEntityTypeHandler implements RequestHandler<DynamicEntityType, CreateEntityTypeCommand> {

    private final CurrentTenantProvider currentTenantProvider;

    private final DynamicEntityTypeRepository dynamicEntityTypeRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public DynamicEntityType handle(CreateEntityTypeCommand cmd) {
        var existing = dynamicEntityTypeRepository.findByCode(cmd.getCode());
        if (existing != null)
            throw new BusinessException(String.format("Mã %s đã tồn tại", cmd.getCode()));

        var dynamicEntityType = DynamicEntityType.builder()
                .code(cmd.getCode())
                .displayName(cmd.getDisplayName())
                .build();
        dynamicEntityType.setTenant(currentTenantProvider.getTenant());

        return dynamicEntityTypeRepository.save(dynamicEntityType);
    }
}
