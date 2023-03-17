package com.metechvn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends  RuntimeException{
    private static final Long seriralVersionUID = 1L ;
    public RecordNotFoundException(String message){
        super(message);
    }

}
