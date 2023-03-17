package com.metechvn.common;

import org.springframework.http.HttpStatus;


public enum ResponseStatus {
    SUCCESS(HttpStatus.OK, 200, "message.success"),
    NOT_COMPANY_LEFT(HttpStatus.OK, 200, "message.not_company_left"),
    CREATED(HttpStatus.CREATED, 2001, "message.created"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "message.bad_request"),

    BAD_CLIENT_REQUEST(HttpStatus.BAD_REQUEST, 400, "message.bad_request"),

    STATUS_HISTORY_NOT_FOUND(HttpStatus.BAD_REQUEST, 4001, "message.status_history_not_found"),
    PACKAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, 4002, "message.package_not_found"),
    CALL_USER_EXIST(HttpStatus.BAD_REQUEST, 4003, "message.call_user_exist"),
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, 4004, "message.user_not_exist"),
    USER_OR_JOB_ID_NULL(HttpStatus.BAD_REQUEST, 4005, "message.user_or_job_id_null"),
    POTENTIAL_COMPANY_NOT_FOUND(HttpStatus.BAD_REQUEST, 4006, "message.potential_company_not_found"),
    IMAGE_TYPE_NOT_VALID(HttpStatus.BAD_REQUEST, 4007, "message.image_type_not_valid"),
    FILE_TYPE_NOT_VALID(HttpStatus.BAD_REQUEST, 4008, "message.file_type_not_valid"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "message.unauthorized"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "message.internal_server_error"),
    FAILED_TO_CREATE_CANDIDATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "message.failed_to_create_candidate");

    private HttpStatus httpStatus;
    private int statusCode;
    private String message;
    ResponseStatus(HttpStatus httpStatus, int statusCode, String message) {
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.message = message;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
