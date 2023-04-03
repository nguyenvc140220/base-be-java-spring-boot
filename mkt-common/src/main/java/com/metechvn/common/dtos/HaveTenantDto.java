package com.metechvn.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class HaveTenantDto<T extends Serializable> extends BaseDto<T> {

    private String tenant;

}
