package com.metechvn.dynamic.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metechvn.common.FullAuditDto;
import com.metechvn.common.persistent.FullAuditedEntity;
import com.metechvn.dynamic.entities.DynamicProperty;
import com.metechvn.validators.dtos.DynamicTypeValidator;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PropertyListDto extends FullAuditDto<UUID, UUID> {

    @Builder
    public PropertyListDto(
            UUID id,
            Long creationTime,
            UUID createdBy,
            Long lastModificationTime,
            UUID lastModificationBy,
            Long deletedTime,
            UUID deletedBy,
            String code,
            String displayName,
            String dataType,
            String validators,
            String defaultValue,
            String hintText,
            String tooltip,
            boolean removable,
            boolean editable) {
        super(id, creationTime, createdBy, lastModificationTime, lastModificationBy, deletedTime, deletedBy);
        this.code = code;
        this.displayName = displayName;
        this.validators = this.tryParseValidators(validators);
        this.dataType = dataType;
        this.removable = removable;
        this.editable = editable;
        this.defaultValue = defaultValue;
        this.hintText = hintText;
        this.tooltip = tooltip;
    }

    public static PropertyListDto of(DynamicProperty p) {
        var dto = PropertyListDto.builder().build();
        dto.apply(p);

        return dto;
    }

    @Override
    protected <E extends FullAuditedEntity<UUID, UUID>> void apply(E e) {
        super.apply(e);

        if (!(e instanceof DynamicProperty p)) return;

        this.code = p.getCode();
        this.displayName = p.getDisplayName();
        this.editable = p.getEditable();
        this.removable = p.getRemovable();
        this.dataType = p.getDataType() == null ? null : p.getDataType().toString();
        this.validators = this.tryParseValidators(p.getValidators());
        this.defaultValue = p.getDefaultValue();
        this.hintText = p.getHintText();
        this.tooltip = p.getTooltip();
    }

    private List<DynamicTypeValidator> tryParseValidators(String json) {
        if (StringUtils.isEmpty(json)) return Collections.emptyList();

        try {
            return new ObjectMapper().readValue(json, new TypeReference<>() { });
        } catch (JsonProcessingException e) {
            return Collections.emptyList();
        }
    }

    private String code;
    private String displayName;
    private String dataType;
    private boolean removable;
    private boolean editable;
    private List<DynamicTypeValidator> validators;
    private String defaultValue;
    private String hintText;
    private String tooltip;

}
