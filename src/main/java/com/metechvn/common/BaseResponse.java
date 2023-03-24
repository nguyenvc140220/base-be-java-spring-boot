package com.metechvn.common;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Locale;

@Data
@JsonIgnoreProperties({"status"})
@NoArgsConstructor
//@Builder
public class BaseResponse<T> implements Serializable {
    private T data;
    private HttpStatus status;
    private int statusCode;
    private String message;

    public BaseResponse(T data, HttpStatus status, int statusCode, String message) {
        this.data = data;
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }

    public static <T> BaseResponse<T> onOk(ResourceBundleMessageSource ms, Locale locale) {
        ResponseStatus status = ResponseStatus.SUCCESS;
        return new BaseResponse<>(null, status.getHttpStatus(), status.getStatusCode(), getMessageInternationalization(status, ms, locale));
    }

    public static <T> BaseResponse<T> onOk(T data) {
        ResponseStatus success = ResponseStatus.SUCCESS;
        return new BaseResponse<>(data, success.getHttpStatus(), success.getStatusCode(), success.getMessage());
    }

    @NotNull
    private static String getMessageInternationalization(ResponseStatus status, ResourceBundleMessageSource ms, Locale locale) {
        return ms.getMessage(status.getMessage(), null, locale);
    }

    public static <T> BaseResponse<T> onOk(T data, ResourceBundleMessageSource ms, Locale locale) {
        ResponseStatus status = ResponseStatus.SUCCESS;
        return new BaseResponse<>(data, status.getHttpStatus(), status.getStatusCode(), getMessageInternationalization(status, ms, locale));
    }

    public static <T> BaseResponse<T> onCreated(ResourceBundleMessageSource ms, Locale locale) {
        ResponseStatus status = ResponseStatus.CREATED;
        return new BaseResponse<>(null, status.getHttpStatus(), status.getStatusCode(), getMessageInternationalization(status, ms, locale));
    }

    public static <T> BaseResponse<T> onCreated(T data) {
        ResponseStatus status = ResponseStatus.CREATED;
        return new BaseResponse<>(data, status.getHttpStatus(), status.getStatusCode(), "Created");
    }

    public static <T> BaseResponse<T> onCreated(T data, ResourceBundleMessageSource ms, Locale locale) {
        ResponseStatus status = ResponseStatus.CREATED;
        return new BaseResponse<>(data, status.getHttpStatus(), status.getStatusCode(), getMessageInternationalization(status, ms, locale));
    }


    public static <T> BaseResponse<T> onError(ResponseStatus status, ResourceBundleMessageSource ms, Locale locale) {
        return new BaseResponse<>(null, status.getHttpStatus(), status.getStatusCode(), getMessageInternationalization(status, ms, locale));
    }

    public static <T> BaseResponse<T> onError(T data, int statusCode, String message) {
        return new BaseResponse<>(data, HttpStatus.valueOf(statusCode), statusCode, message);
    }

    @SneakyThrows
    public static <T> BaseResponse<T> onClientError(String exception) {
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(exception, BaseResponse.class);
        return new BaseResponse<>(null, HttpStatus.OK, baseResponse.getStatusCode(), baseResponse.getMessage());
    }

    public static <T> BaseResponse<T> onUnauthorizedError(ResourceBundleMessageSource ms, Locale locale) {
        ResponseStatus status = ResponseStatus.UNAUTHORIZED;
        return new BaseResponse<>(null, status.getHttpStatus(), status.getStatusCode(), getMessageInternationalization(status, ms, locale));
    }

    public static <T> BaseResponse<T> onInternalServerError(ResourceBundleMessageSource ms, Locale locale) {
        ResponseStatus code = ResponseStatus.INTERNAL_SERVER_ERROR;
        return new BaseResponse<>(null, code.getHttpStatus(), code.getStatusCode(), getMessageInternationalization(code, ms, locale));
    }
}
