package com.metechvn.dynamic.dtos;

import com.metechvn.common.FullAuditDto;
import com.metechvn.common.persistent.FullAuditedEntity;
import com.metechvn.dynamic.DataType;
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
            DataType dataType) {
        super(id, creationTime, createdBy, lastModificationTime, lastModificationBy, deletedTime, deletedBy);
        this.code = code;
        this.displayName = displayName;
        this.dataType = dataType;
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
        this.dataType = p.getDataType();
        this.editable = p.getEditable();
        this.removable = p.getRemovable();
    }

    private String code;
    private String displayName;

    private DataType dataType;
    private boolean editable;
    private boolean removable;

}
