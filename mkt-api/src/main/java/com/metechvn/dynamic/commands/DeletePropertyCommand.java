package com.metechvn.dynamic.commands;

import com.metechvn.dynamic.entities.DynamicProperty;
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
public class DeletePropertyCommand implements Request<DynamicProperty> {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Mã kĩ thuật chỉ bao gồm chữ cái, số và dấu gạch dưới")
    private String code;

}
