package com.metechvn.dynamic.commands;

import com.metechvn.dynamic.DataType;
import com.metechvn.dynamic.DynamicInputType;
import com.metechvn.dynamic.entities.DynamicProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import luongdev.cqrs.Request;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePropertyCommand implements Request<DynamicProperty> {

    @Size(min = 2, max = 50)
    private String displayName;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Mã kĩ thuật chỉ bao gồm chữ cái, số và dấu gạch dưới")
    private String code;

    @Enumerated(EnumType.STRING)
    private DataType dataType;

    private DynamicInputType inputType;

    private String validators;

    @Builder.Default
    private boolean removable = true;

    @Builder.Default
    private boolean updatable = true;

    private Boolean editable;
}
