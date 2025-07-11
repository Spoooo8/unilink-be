package com.unilink.common.exceptions;

import com.unilink.common.dto.ExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CommonExceptionHandler extends Exception{
    Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<ExceptionDto> handleBadRequestException(BadRequestException ex)  {
//        logger.error("Exception occurred: {}", ex.getMessage());
//        ExceptionDto response = new ExceptionDto(ex.getMessage(), ex.getStatusCode());
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(UnilinkBadRequestException.class)
    public ResponseEntity<ExceptionDto> handleResourceNotFoundException(UnilinkBadRequestException ex)  {
        logger.error("Exception occurred: {}", ex.getMessage());
        ExceptionDto response = new ExceptionDto(ex.getMessage(), ex.getStatusCode());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}