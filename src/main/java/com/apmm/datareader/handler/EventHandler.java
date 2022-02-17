package com.apmm.datareader.handler;


import com.apmm.datareader.dto.EventDto;
import com.apmm.datareader.entity.Event;
import com.apmm.datareader.service.EventService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EventHandler {

    private final EventService eventService;

    public Mono<ServerResponse> loadEvents(ServerRequest request){

        Flux<EventDto> eventList = eventService.getEvents();
        return ServerResponse.ok().body(eventList,EventDto.class);
    }

    public  Mono<ServerResponse> loadEventById(ServerRequest request){
        Mono<EventDto> event = eventService.getEventById(request.pathVariable("eventId"));
        return ServerResponse.ok().body(event,EventDto.class);
    }

    public  Mono<ServerResponse> saveEvent(ServerRequest request){
        Mono<EventDto> event = eventService.save(request.bodyToMono(Event.class));
        return ServerResponse.ok().body(event,EventDto.class);
    }

    public Mono<ServerResponse> updateEvent(ServerRequest request) {
        Mono<EventDto> event = eventService.updateEventByEventId(request.bodyToMono(EventDto.class), request.pathVariable("eventId"));
        return ServerResponse.ok().body(event,EventDto.class);
    }
}
