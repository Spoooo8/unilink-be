package com.unilink.quiz_service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    int statusCode = 404;
    public ResourceNotFoundException(String message) {
        super((message));
    }
}