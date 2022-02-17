package com.apmm.datareader.router;


import com.apmm.datareader.constants.Constants;
import com.apmm.datareader.dto.EventDto;
import com.apmm.datareader.handler.EventHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final EventHandler eventHandler;

    @Bean
    @RouterOperations({
            @RouterOperation(path="/router/events",
                    produces = {
                            MediaType.APPLICATION_JSON_VALUE
                    },
                    method= RequestMethod.GET,
                    beanClass = EventHandler.class,
                    beanMethod = Constants.LOAD_EVENTS,
                    operation = @Operation(
                            operationId = Constants.LOAD_EVENTS,
                            responses = {
                                    @ApiResponse(
                                            responseCode = Constants.HTTP_STATUS_OK,
                                            description = Constants.SUCCESS_OPERATION,
                                            content = @Content(schema = @Schema(
                                                    implementation = EventDto.class
                                            ))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path="/router/event/{eventId}",
                    produces = {
                            MediaType.APPLICATION_JSON_VALUE
                    },
                    method= RequestMethod.GET,
                    beanClass = EventHandler.class,
                    beanMethod = Constants.LOAD_EVENT_ID,
                    operation = @Operation(
                            operationId = Constants.LOAD_EVENT_ID,
                            responses = {
                                    @ApiResponse(
                                            responseCode = Constants.HTTP_STATUS_OK,
                                            description = Constants.SUCCESS_OPERATION,
                                            content = @Content(schema = @Schema(
                                                    implementation = EventDto.class
                                            ))
                                    ),
                                    @ApiResponse(
                                            responseCode = Constants.HTTP_STATUS_NOT_FOUND,
                                            description = Constants.ERR_MSG_404,
                                            content = @Content(schema = @Schema(
                                                    implementation = EventDto.class
                                            )
                                            )
                                    )
                            },parameters = {@Parameter(in= ParameterIn.PATH,name = "eventId")}
                    )
            )
    }
    )
    public RouterFunction<ServerResponse> routerFunction(){

        return RouterFunctions.route()
                .GET("/router/events",eventHandler::loadEvents)
                .GET("/router/event/{eventId}",eventHandler::loadEventById)
                .POST("/router/event/save",eventHandler::saveEvent)
                .PUT("/router/event/update/{eventId}",eventHandler::updateEvent)
                .build();
    }
}
