package com.apmm.datareader.router;


import com.apmm.datareader.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final EventHandler eventHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){

        return RouterFunctions.route()
                .GET("/router/events",eventHandler::loadEvents)
                .GET("/router/event/{eventId}",eventHandler::loadEventById)
                .POST("/router/event/save",eventHandler::saveEvent)
                .PUT("/router/event/update/{eventId}",eventHandler::updateEvent)
                .build();
    }
}
