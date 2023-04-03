package com.metechvn.dynamic.entities;

import com.metechvn.common.persistent.UUIDFullAuditedEntityImpl;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dynamic_entity_property")
public class DynamicEntityProperty extends UUIDFullAuditedEntityImpl {

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "entity_id")
    private DynamicEntity entity;

    @ManyToOne
    @JoinColumn(name = "entity_type_property_id")
    private DynamicEntityTypeProperty entityProperty;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "entity_property_value_id")
    private DynamicEntityPropertyValue entityPropertyValue;

    public DynamicEntityProperty(DynamicEntity entity, DynamicEntityTypeProperty property, Object value) {
        this();

        this.entity = entity;
        this.entityProperty = property;
        this.code = property.getCode();
        this.entityPropertyValue = new DynamicEntityPropertyValue(value == null ? null : String.valueOf(value));
    }
}
