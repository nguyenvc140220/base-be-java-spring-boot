package com.metechvn.contacts.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metechvn.common.FullAuditDto;
import com.metechvn.common.persistent.FullAuditedEntity;
import com.metechvn.contacts.entities.Segmentation;
import com.metechvn.filter.Expression;
import com.metechvn.filter.Filter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SegmentationListDto extends FullAuditDto<UUID, UUID> {

    private String name;
    private int numOfContacts = 0;
    private final List<Expression> filters = new ArrayList<>();
    @Builder
    public SegmentationListDto(
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
        super(id, creationTime, createdBy, lastModificationTime, lastModificationBy, deletedTime, deletedBy);
        this.name = name;
        this.numOfContacts = numOfContacts;
        if (filters != null && !filters.isEmpty()) this.filters.addAll(filters);
    }

    protected SegmentationListDto() {
    }

    @Override
    protected <E extends FullAuditedEntity<UUID, UUID>> void apply(E e) {
        super.apply(e);

        if (!(e instanceof Segmentation seg)) return;

        this.name = seg.getName();
        this.numOfContacts = seg.getNumOfContacts();

        if (seg.getFilters() == null || seg.getFilters().isEmpty()) return;

        var om = new ObjectMapper();
        for (var filter : seg.getFilters()) {
            if (StringUtils.isEmpty(filter.getFilters())) continue;

            try {
                var exp = om.readValue(filter.getFilters(), Filter.class);
                this.filters.add(exp);
            } catch (JsonProcessingException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static SegmentationListDto of(Segmentation segmentation) {
        var dto = new SegmentationListDto();
        dto.apply(segmentation);

        return dto;
    }
}
