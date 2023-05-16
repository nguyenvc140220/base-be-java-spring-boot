package com.metechvn.dynamic.commands.handlers;

import com.metechvn.dynamic.commands.EntityTypeAddPropertyCommand;
import com.metechvn.dynamic.entities.DynamicEntityType;
import com.metechvn.dynamic.entities.DynamicProperty;
import com.metechvn.dynamic.repositories.DynamicEntityTypeRepository;
import com.metechvn.dynamic.repositories.DynamicPropertyRepository;
import com.metechvn.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import luongdev.cqrs.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class EntityTypeAddPropertyHandler implements RequestHandler<DynamicEntityType, EntityTypeAddPropertyCommand> {

    private final DynamicEntityTypeRepository dynamicEntityTypeRepository;
    private final DynamicPropertyRepository dynamicPropertyRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public DynamicEntityType handle(EntityTypeAddPropertyCommand cmd) {
        var existing = dynamicEntityTypeRepository.findByCode(cmd.getEntityTypeCode());
        if (existing == null)
            throw new BusinessException(String.format("Mã %s không tồn tại", cmd.getEntityTypeCode()));

        ArrayList<DynamicProperty> properties = new ArrayList<>();
        for (var code : cmd.getPropertyCodes()) {
            var property = dynamicPropertyRepository.findByCode(code);
            if (property != null) {
                properties.add(property);
            }
        }
        if (properties.size() > 0) {
            DynamicProperty[] dynamicProperties = new DynamicProperty[properties.size()];
            existing.addProperties(properties.toArray(dynamicProperties));
        }
        return dynamicEntityTypeRepository.save(existing);
    }
}
