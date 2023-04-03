package com.metechvn.common;

import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
    public static <T> ResponseEntity<BaseResponse<T>> build(BaseResponse<T> response) {
        return new ResponseEntity<>(response, response.getStatus());
    }
}
