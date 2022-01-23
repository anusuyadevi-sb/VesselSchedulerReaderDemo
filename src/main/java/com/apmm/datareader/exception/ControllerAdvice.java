package com.apmm.datareader.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.MethodNotAllowedException;
import reactor.core.publisher.Mono;

@org.springframework.web.bind.annotation.ControllerAdvice
@Slf4j
public class ControllerAdvice{

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Mono<ErrorResponse> badRequest(BadRequestException exception){
        ErrorResponse response = new ErrorResponse();
        response.getErrors()
                .add(
                        new Error(
                                HttpStatus.BAD_REQUEST,
                                "Bad Request",
                                exception.getMessage()
                        ));
        return Mono.just(response);
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody

    public Mono<ErrorResponse> dataNotAvailable(DataNotFoundException exception){
        ErrorResponse response=new ErrorResponse();
        response.getErrors()
                .add(
                        new Error(
                        HttpStatus.NOT_FOUND,
                        "Data not Available",
                        exception.getMessage()
                ));
        return Mono.just(response);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Mono<ErrorResponse> internalServerError(Exception exception){
        ErrorResponse response = new ErrorResponse();
        response.getErrors()
                .add(
                        new Error(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Internal Server Error",
                                exception.getLocalizedMessage()
                        ));
        return Mono.just(response);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public Mono<ErrorResponse> methodNotAllowed(MethodNotAllowedException exception){
        ErrorResponse response=new ErrorResponse();
        response.getErrors()
                .add(
                        new Error(
                                HttpStatus.METHOD_NOT_ALLOWED,
                                "Method is not allowed",
                                exception.getLocalizedMessage()
                        ));
        return Mono.just(response);
    }

}


