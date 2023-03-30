package com.metechvn.resource.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportStatusDto {

    private String fileName;
    private String jobId;
    private int totalRows;
    private int successRows;
    private int errorRows;
    private double successRatio;
    private double errorRatio;

}
