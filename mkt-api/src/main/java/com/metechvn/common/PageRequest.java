package com.metechvn.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PageRequest {
    int pageNumber;
    int pageSize;

}
