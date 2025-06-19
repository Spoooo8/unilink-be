package com.unilink.common.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnilinkResourceNotFoundException extends RuntimeException {
    int statusCode = 404;
    public UnilinkResourceNotFoundException(String message) {
        super((message));
    }
}