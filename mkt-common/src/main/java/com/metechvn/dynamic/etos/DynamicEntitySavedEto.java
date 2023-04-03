package com.metechvn.dynamic.etos;

import com.metechvn.common.etos.HaveTenantEto;
import com.metechvn.dynamic.dtos.DynamicEntityPropertyDto;
import com.metechvn.dynamic.dtos.DynamicEntityTypeDto;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DynamicEntitySavedEto extends HaveTenantEto<UUID> {

    private DynamicEntityTypeDto entityType;
    @Builder.Default
    private Map<String, DynamicEntityPropertyDto> properties = new HashMap<>();
}
