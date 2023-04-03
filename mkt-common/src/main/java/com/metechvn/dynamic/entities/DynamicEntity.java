package com.metechvn.dynamic.entities;

import com.metechvn.common.persistent.UUIDFullAuditedEntityImpl;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dynamic_entity")
public class DynamicEntity extends UUIDFullAuditedEntityImpl {

    @ManyToOne
    @JoinColumn(name = "entity_type_id")
    private DynamicEntityType entityType;

    @Builder.Default
    @MapKey(name = "code")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entity")
    private Map<String, DynamicEntityProperty> properties = new HashMap<>();

    public DynamicEntity set(DynamicEntityTypeProperty property, Object value) {
        if (property == null) return this;

        properties.put(property.getCode(), new DynamicEntityProperty(this, property, value));

        return this;
    }
}
