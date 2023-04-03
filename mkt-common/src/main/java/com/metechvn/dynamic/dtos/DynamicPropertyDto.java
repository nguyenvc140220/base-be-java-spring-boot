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
public class DynamicPropertyDto extends HaveTenantDto<UUID> {
    private String code;
    private DataType dataType;

}
