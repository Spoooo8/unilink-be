package com.unilink.quiz_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDto {
    private Integer statusCode;
    private String message;
    private String messageType = "INFO";


    public ExceptionDto(String message, int statusCode) {
        this.statusCode = statusCode;
        this.message = message;
    }
}