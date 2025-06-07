package com.unilink.quiz_service.exception;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class BadRequestException extends RuntimeException{
    int statusCode = 404;

    public BadRequestException(String message) {
        super(message);
    }
}