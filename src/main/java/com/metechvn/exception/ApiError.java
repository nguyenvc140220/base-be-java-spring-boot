package com.metechvn.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * ApiError
 *
 * @author AnhDT118
 * @since 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String msg;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String debugMessage;
    //    private List<ApiSubError> subErrors;
    private Object detail;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.msg = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.msg = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
