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
@Table(name = "join_dynamic_entity_type_property")
public class DynamicEntityTypeProperty extends UUIDFullAuditedEntityImpl {

    @Column(name = "code")
    private String code;

    @Column(name = "view_role")
    private String viewRole;

    @Column(name = "create_role")
    private String createRole;

    @Column(name = "update_role")
    private String updateRole;

    @Column(name = "delete_role")
    private String deleteRole;

    @JoinColumn(name = "property_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private DynamicProperty property;

    @ManyToOne
    @JoinColumn(name = "entity_type_id")
    private DynamicEntityType entityType;

    public DynamicEntityTypeProperty(DynamicEntityType entityType, DynamicProperty property) {
        this();

        this.entityType = entityType;
        this.property = property;
        this.code = property.getCode();
    }
}
