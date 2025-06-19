package com.unilink.common.exceptions;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UnilinkBadRequestException extends RuntimeException{
    int statusCode = 404;

    public UnilinkBadRequestException(String message) {
        super(message);
    }
}