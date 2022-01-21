package com.apmm.datareader.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;
import com.apmm.datareader.exception.Error;

@org.springframework.web.bind.annotation.ControllerAdvice

public class ControllerAdvice{
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

   /* @ExceptionHandler(DatabaseNotAvailable.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody

    public Mono<ErrorResponse> databaseNotAvailable(DatabaseNotAvailable exception){
        ErrorResponse response=new ErrorResponse();
        response.getErrors()
                .add(
                        new Error(
                                HttpStatus.NOT_FOUND,
                                "Database is currently unavailable.Try again later",
                                exception.getMessage()
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
                                "Only Get Method is allowed",
                                "Get method is allowed"
                        ));
        return Mono.just(response);
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody

    public Mono<ErrorResponse> internalServerError(Exception exception){
        ErrorResponse response=new ErrorResponse();
        response.getErrors()
                .add(
                        new Error(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Server not available try again later",
                                "Get method is allowed"
                        ));
        return Mono.just(response);
    }
*/
}


