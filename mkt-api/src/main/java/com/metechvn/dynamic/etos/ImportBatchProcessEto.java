package com.metechvn.dynamic.etos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportBatchProcessEto {

    private String fileName;
    private int successRows;
    private int errorRows;
    private int totalRows;


}
