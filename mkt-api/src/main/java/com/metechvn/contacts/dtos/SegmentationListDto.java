package com.metechvn.contacts.dtos;

import com.metechvn.common.FullAuditDto;
import com.metechvn.common.persistent.FullAuditedEntity;
import com.metechvn.contacts.entities.Segmentation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SegmentationListDto extends FullAuditDto<UUID, UUID> {

    private String name;

    @Builder
    public SegmentationListDto(
            UUID id,
            Long creationTime,
            UUID createdBy,
            Long lastModificationTime,
            UUID lastModificationBy,
            Long deletedTime,
            UUID deletedBy,
            String name) {
        super(id, creationTime, createdBy, lastModificationTime, lastModificationBy, deletedTime, deletedBy);
        this.name = name;
    }

    protected SegmentationListDto() {
    }

    @Override
    protected <E extends FullAuditedEntity<UUID, UUID>> void apply(E e) {
        super.apply(e);

        if (!(e instanceof Segmentation seg)) return;

        this.name = seg.getName();
    }

    public static SegmentationListDto of(Segmentation segmentation) {
        var dto = new SegmentationListDto();
        dto.apply(segmentation);

        return dto;
    }
}
