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
public class DynamicEntityPropertyDto extends HaveTenantDto<UUID> {
    private String propertyCode;
    private String entityPropertyValue;

}
