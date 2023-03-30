package com.metechvn.dynamic.dtos;

import com.metechvn.common.FullAuditDto;
import com.metechvn.common.persistent.FullAuditedEntity;
import com.metechvn.dynamic.entities.DynamicProperty;
import lombok.*;

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
            String dataType,
            String displayName,
            String validators,
            boolean editable,
            boolean removable) {
        super(id, creationTime, createdBy, lastModificationTime, lastModificationBy, deletedTime, deletedBy);
        this.code = code;
        this.dataType = dataType;
        this.displayName = displayName;
        this.validators = validators;
        this.editable = editable;
        this.removable = removable;
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
        this.dataType = p.getDataType() == null ? null : p.getDataType().toString();
        this.editable = p.getEditable();
        this.removable = p.getRemovable();
        this.validators = p.getValidators();
    }

    private String code;
    private String dataType;
    private String validators;
    private String displayName;
    private boolean editable;
    private boolean removable;
}
