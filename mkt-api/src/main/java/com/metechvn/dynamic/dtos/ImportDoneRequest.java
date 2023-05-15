package com.metechvn.dynamic.dtos;

import lombok.*;

import javax.annotation.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImportDoneRequest {
    @Nullable
    private String fileName;
    @Nullable
    private int totalRows;
    @Nullable
    private int successRows;
    @Nullable
    private int errorRows;
/*    private UUID idUser;*/
}
