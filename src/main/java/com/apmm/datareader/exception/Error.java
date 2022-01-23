package com.apmm.datareader.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
public class Error  {
    private HttpStatus status;
    private String resource;
    private String message;


}
