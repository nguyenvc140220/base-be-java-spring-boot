package com.metechvn.contacts.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metechvn.common.persistent.FullAuditedEntity;
import com.metechvn.contacts.entities.Segmentation;
import com.metechvn.filter.Expression;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SegmentationDto extends SegmentationListDto {

    private final List<Expression> filters = new ArrayList<>();

    protected SegmentationDto() {
    }

    public SegmentationDto(
            UUID id,
            Long creationTime,
            UUID createdBy,
            Long lastModificationTime,
            UUID lastModificationBy,
            Long deletedTime,
            UUID deletedBy,
            String name,
            int numOfContacts,
            List<Expression> filters) {
        super(
                id,
                creationTime,
                createdBy,
                lastModificationTime,
                lastModificationBy,
                deletedTime,
                deletedBy,
                name,
                numOfContacts,
                filters
        );

        if (filters != null && !filters.isEmpty()) this.filters.addAll(filters);
    }

    @Override
    protected <E extends FullAuditedEntity<UUID, UUID>> void apply(E e) {
        super.apply(e);

        if (!(e instanceof Segmentation seg)) return;

        if (seg.getFilters() == null || seg.getFilters().isEmpty()) return;

        var om = new ObjectMapper();
        for (var filter : seg.getFilters()) {
            if (StringUtils.isEmpty(filter.getFilters())) continue;

            try {
                var exp = om.readValue(filter.getFilters(), Expression.class);
                this.filters.add(exp);
            } catch (JsonProcessingException ignored) {
            }
        }
    }

    public static SegmentationDto of(Segmentation segmentation) {
        var dto = new SegmentationDto();
        dto.apply(segmentation);

        return dto;
    }
}
