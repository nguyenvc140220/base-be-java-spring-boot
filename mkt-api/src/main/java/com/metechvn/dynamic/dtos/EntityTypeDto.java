package com.metechvn.dynamic.dtos;

import com.metechvn.common.FullAuditDto;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class EntityTypeDto extends FullAuditDto<UUID, UUID> {
    private String code;
    private String displayName;

    @Builder
    public EntityTypeDto(
            UUID id,
            String code,
            String displayName,
            Long creationTime,
            UUID createdBy,
            Long lastModificationTime,
            UUID lastModificationBy,
            Long deletedTime,
            UUID deletedBy) {
        super(id, creationTime, createdBy, lastModificationTime, lastModificationBy, deletedTime, deletedBy);

        this.code = code;
        this.displayName = displayName;
    }
}
