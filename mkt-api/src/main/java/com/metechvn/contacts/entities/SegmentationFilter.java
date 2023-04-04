package com.metechvn.contacts.entities;

import com.metechvn.common.persistent.UUIDFullAuditedEntityImpl;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "segmentation_filter")
public class SegmentationFilter extends UUIDFullAuditedEntityImpl {

    @Column(name = "name")
    private String name;

    @Column(name = "filters", length = 4080)
    private String filters;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "segmentationFilters")
    private List<Segmentation> segmentations = new ArrayList<>();

    @Builder
    public SegmentationFilter(String name, String filters, List<Segmentation> segmentations) {
        this();

        this.name = name;
        this.filters = filters;
        this.segmentations = segmentations;
    }
}
