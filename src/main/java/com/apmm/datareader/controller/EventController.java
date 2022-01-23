package com.apmm.datareader.controller;

import com.apmm.datareader.dto.EventDto;
import com.apmm.datareader.entity.Event;
import com.apmm.datareader.exception.BadRequestException;
import com.apmm.datareader.service.EventService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Pattern;

@RestController
@Slf4j

public class EventController {

    @Autowired
    private EventService service;

    @GetMapping("/events")
    public Flux<EventDto> getEvents()  {
        log.debug("Controller method in getEvents");
        return service.getEvents();
    }

    @GetMapping("/event/{id}")

        public Mono<EventDto> getEventById(@PathVariable ("id")  String id)  throws BadRequestException  {

        log.debug("Controller method in getEventById");
        if (StringUtil.isNullOrEmpty(id)){
            throw new BadRequestException("Please provide id for url : /event/{id}");
        }
        return service.getEventById(id);


    }

    @PostMapping
    public Mono<EventDto> save(@RequestBody Mono<Event> employeeDtoMono) {
        return service.save(employeeDtoMono);
    }

    @PutMapping("event/update/{id}")
    public Mono<EventDto> updateEvent(@RequestBody Mono<EventDto> eventDtoMono, @PathVariable String id){
        return service.updateEvent(eventDtoMono, id);
    }
}
