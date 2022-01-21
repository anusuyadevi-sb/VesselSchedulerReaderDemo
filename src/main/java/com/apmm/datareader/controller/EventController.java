package com.apmm.datareader.controller;

import com.apmm.datareader.dto.EventDto;
import com.apmm.datareader.entity.Event;
import com.apmm.datareader.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService service;

    @GetMapping
    public Flux<Event> getEvents()  {
        log.debug("Controller method in getEvents");
        return service.getEvents();
    }

    @GetMapping("/{id}")
        public Mono<Event> getEventById(@PathVariable("id")  String id)    {
        log.debug("Controller method in getEventById");
        return service.getEventById(id);


    }

    @PostMapping
    public Mono<EventDto> save(@RequestBody Mono<Event> employeeDtoMono) {
        return service.save(employeeDtoMono);
    }

    @PostMapping ("/dbCall")

    public String FromDb(@RequestBody String message) {
        System.out.println("DB call::"+message);
        return "Hello World";
    }

    @GetMapping("/deployed")
    public String message(){
        return "Application deployed successfully in azure";
    }
}
