package com.metechvn.dynamic.dtos;

import com.metechvn.common.dtos.HaveTenantDto;
import com.metechvn.dynamic.DataType;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DynamicEntityTypeDto extends HaveTenantDto<UUID> {
    private String code;
    private String displayName;
    private String description;
}
