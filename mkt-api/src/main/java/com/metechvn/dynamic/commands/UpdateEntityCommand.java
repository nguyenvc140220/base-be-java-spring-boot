package com.metechvn.dynamic.commands;

import com.metechvn.dynamic.entities.DynamicEntity;
import lombok.*;
import luongdev.cqrs.Request;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEntityCommand implements Request<DynamicEntity> {
    @NotNull
    @NotEmpty
    private String code;

    @NotNull
    @NotEmpty
    private UUID IdEntity;

    @Builder.Default
    private final Map<String, Object> properties = new HashMap<>();

    public UpdateEntityCommand put(String key, Object value) {
        if (StringUtils.hasText(key)) this.properties.put(key, value);

        return this;
    }
}
