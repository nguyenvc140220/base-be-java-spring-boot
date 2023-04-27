package com.metechvn.dynamic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.metechvn.common.persistent.UUIDFullAuditedEntityImpl;
import com.metechvn.dynamic.DataType;
import com.metechvn.dynamic.DynamicInputType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dynamic_property")
public class DynamicProperty extends UUIDFullAuditedEntityImpl {

    @Column(name = "code")
    private String code;

    @Column(name = "display_name")
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type")
    private DataType dataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "input_type")
    private DynamicInputType inputType;

    @Column(name = "validators", length = 2040)
    private String validators;

    @Column(name = "hint_text")
    private String hintText;

    @Column(name = "tooltip")
    private String tooltip;

    @Column(name = "default_value", length = 4080)
    private String defaultValue;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "editable")
    private Boolean editable;

    @Column(name = "configurable")
    private Boolean configurable;

    @Column(name = "removable")
    private Boolean removable;

    @Column(name = "hidden")
    private Boolean hidden;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DynamicEntityTypeProperty> dynamicEntityTypeProperties;
}
