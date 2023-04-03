package com.metechvn.common.etos;

import com.metechvn.common.dtos.FlattenDynamicEntityDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DynamicEntitySavedEto extends HaveTenantEto<UUID> {
    private String entityType;
    @Builder.Default
    private List<FlattenDynamicEntityDto<UUID>> batches = new ArrayList<>();
}