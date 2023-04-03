package com.metechvn.dynamic.commands;

import com.metechvn.dynamic.entities.DynamicEntityType;
import jakarta.validation.constraints.*;
import lombok.*;
import luongdev.cqrs.Request;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEntityTypeCommand implements Request<DynamicEntityType> {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    private String displayName;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Mã kĩ thuật chỉ bao gồm chữ cái, số và dấu gạch dưới")
    private String code;

    @Builder.Default
    private boolean removable = true;

    @Builder.Default
    private boolean updatable = true;
}
