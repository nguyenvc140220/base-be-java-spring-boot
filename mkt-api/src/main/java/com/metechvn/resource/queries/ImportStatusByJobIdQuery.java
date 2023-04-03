package com.metechvn.resource.queries;

import com.metechvn.resource.dtos.ImportStatusDto;
import lombok.*;
import luongdev.cqrs.Request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportStatusByJobIdQuery implements Request<ImportStatusDto> {

    @NotNull
    @NotEmpty
    private String jobId;

}
