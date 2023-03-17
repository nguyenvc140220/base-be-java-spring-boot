package com.metechvn.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BaseRequest {

    @Schema(example = "")
    private String keyword;
    @Schema(example = "10")
    private Integer pageSize = 10;
    @Schema(example = "1")
    private Integer pageNumber = 1;
}
