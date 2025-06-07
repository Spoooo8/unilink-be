package com.unilink.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class FQInternalServerError extends RuntimeException {
    public FQInternalServerError(String message) {
        super(message);
    }
}