package com.metechvn.dynamic.commands.handlers;

import com.metechvn.dynamic.commands.UpdatePropertyCommand;
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
public class UpdatePropertyHandler implements RequestHandler<DynamicProperty, UpdatePropertyCommand> {

    private final DynamicPropertyRepository dynamicPropertyRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public DynamicProperty handle(@NotNull UpdatePropertyCommand cmd) {
        var existing = dynamicPropertyRepository.findByCode(cmd.getCode());
        if (existing == null)
            throw new BusinessException(String.format("Mã %s không tồn tại", cmd.getCode()));
        if(!cmd.getDisplayName().isEmpty())
            existing.setDisplayName(cmd.getDisplayName());
        if(cmd.getDataType() != null)
            existing.setDataType(cmd.getDataType());
        if(cmd.getInputType() != null)
            existing.setInputType(cmd.getInputType());
        if (cmd.getEditable() != null) {
            existing.setEditable(cmd.getEditable());
        }
        return dynamicPropertyRepository.save(existing);
    }
}
