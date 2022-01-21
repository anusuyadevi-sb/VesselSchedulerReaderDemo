package com.apmm.datareader.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.BodyInserter;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
public class Error  {
    private HttpStatus status;
    private String resource;
    private String message;


}
