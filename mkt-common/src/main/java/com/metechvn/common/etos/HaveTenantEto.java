package com.metechvn.common.etos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class HaveTenantEto<T extends Serializable> extends BaseEto<T> {
    private String tenant;

}
