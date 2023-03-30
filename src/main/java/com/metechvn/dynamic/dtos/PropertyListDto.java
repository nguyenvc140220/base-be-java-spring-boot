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
            String displayName,
            String dataType,
            boolean removable,
            boolean editable) {
        super(id, creationTime, createdBy, lastModificationTime, lastModificationBy, deletedTime, deletedBy);
        this.code = code;
        this.displayName = displayName;
        this.dataType = dataType;
        this.removable = removable;
        this.editable = editable;
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
    }

    private String code;
    private String displayName;
    private String dataType;
    private boolean removable;
    private boolean editable;

}
