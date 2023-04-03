package com.metechvn.dynamic.etos;

import com.metechvn.common.etos.HaveTenantEto;
import com.metechvn.dynamic.dtos.DynamicPropertyDto;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DynamicEntityTypeSavedEto extends HaveTenantEto<UUID> {

    private String code;

    @Builder.Default
    private Map<String, DynamicPropertyDto> properties = new HashMap<>();
}
