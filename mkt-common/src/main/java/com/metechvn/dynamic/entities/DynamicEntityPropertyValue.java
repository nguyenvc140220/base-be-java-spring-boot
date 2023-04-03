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
@Table(name = "dynamic_entity_property_value")
public class DynamicEntityPropertyValue extends UUIDFullAuditedEntityImpl {

    @Column(name = "value", length = 4080)
    private String value;
}
