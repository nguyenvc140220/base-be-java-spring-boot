package com.metechvn.contacts.entities;

import com.metechvn.common.persistent.UUIDFullAuditedEntityImpl;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "segmentation")
public class Segmentation extends UUIDFullAuditedEntityImpl {

    @Column(name = "name", length = 510)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "join_segmentation_filter",
            joinColumns = @JoinColumn(name = "segmentation_id"),
            inverseJoinColumns = @JoinColumn(name = "segmentation_filter_id")
    )
    private List<SegmentationFilter> segmentationFilters = new ArrayList<>();

    public Segmentation(String name) {
        super();
        this.name = name;
    }

    @Builder
    public Segmentation(UUID id, String name) {
        super(id);
        this.name = name;
    }

    public Segmentation filter(SegmentationFilter... filters) {
        if (filters.length == 0) return this;

        this.segmentationFilters.addAll(List.of(filters));

        return this;
    }
}
