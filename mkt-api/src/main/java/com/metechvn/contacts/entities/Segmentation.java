package com.metechvn.contacts.entities;

import com.metechvn.common.persistent.UUIDFullAuditedEntityImpl;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "segmentation")
public class Segmentation extends UUIDFullAuditedEntityImpl {

    @Column(name = "name", length = 510)
    private String name;

    @Column(name = "num_of_contacts", columnDefinition = "int default 0")
    private int numOfContacts;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "join_segmentation_filter",
            joinColumns = @JoinColumn(name = "segmentation_id"),
            inverseJoinColumns = @JoinColumn(name = "segmentation_filter_id")
    )
    private List<SegmentationFilter> filters = new ArrayList<>();

    public Segmentation() {
        super();
    }

    @Builder
    public Segmentation(String name, int numOfContacts) {
        this();
        this.name = name;
        this.numOfContacts = numOfContacts;
    }

    public Segmentation filter(SegmentationFilter... filters) {
        if (filters.length == 0) return this;

        this.filters.addAll(List.of(filters));

        return this;
    }
}
