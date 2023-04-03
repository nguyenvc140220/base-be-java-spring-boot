package com.metechvn.dynamic.entities;

import com.metechvn.common.persistent.UUIDFullAuditedEntityImpl;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dynamic_entity_type")
public class DynamicEntityType extends UUIDFullAuditedEntityImpl {

    @Column(name = "code")
    private String code;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "description", length = 510)
    private String description;

    @Builder.Default
    @MapKey(name = "code")
    @OneToMany(mappedBy = "entityType", cascade = CascadeType.ALL)
    private Map<String, DynamicEntityTypeProperty> properties = new HashMap<>();

    public DynamicEntityTypeProperty getProperty(String key) {
        if (!StringUtils.hasText(key)) return null;

        return properties.get(key);
    }

    public boolean exists(String key) {
        if (!StringUtils.hasText(key)) return false;

        return properties.containsKey(key);
    }


    public DynamicEntityType addProperties(DynamicProperty... properties) {
        for (var property : properties) {
            if (property == null || this.properties.containsKey(property.getCode())) continue;

            this.properties.put(property.getCode(), new DynamicEntityTypeProperty(this, property));
        }

        return this;
    }

    public DynamicEntityType removeProperties(String... codes) {
        for (var rCode : codes) this.properties.remove(rCode);

        return this;
    }
}
