package com.unilink.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FQBadRequestException extends RuntimeException {
    public FQBadRequestException(String message) {
        super(message);
    }

}