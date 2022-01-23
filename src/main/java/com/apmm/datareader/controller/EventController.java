package com.apmm.datareader.controller;

import com.apmm.datareader.dto.EventDto;
import com.apmm.datareader.entity.Event;
import com.apmm.datareader.exception.BadRequestException;
import com.apmm.datareader.service.EventService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        public Mono<EventDto> getEventById(@PathVariable ("id")  String id)    {
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

    @PostMapping ("/dbCall")

    public String FromDb(@RequestBody String message) {
        log.info("DB call::" + message);
        return "Hello World";
    }

    @GetMapping("/deployed")
    public String message(){
        return "Application deployed successfully in azure";
    }

    @PutMapping("event/update/{id}")
    public Mono<EventDto> updateEvent(@RequestBody Mono<EventDto> eventDtoMono, @PathVariable String id){
        return service.updateEvent(eventDtoMono, id);
    }
}
