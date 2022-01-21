package com.apmm.datareader.service;

import com.apmm.datareader.dto.EventDto;
import com.apmm.datareader.entity.Event;
import com.apmm.datareader.exception.DataNotFoundException;
import com.apmm.datareader.repository.EventRepository;
import com.apmm.datareader.utils.AppUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Slf4j
@Service
@AllArgsConstructor
public class EventService {
    @Autowired
    private EventRepository repository;

    public Flux<Event> getEvents() {
        log.debug("Service method in getEvents");
        return repository.findAll()

                .switchIfEmpty(Mono.defer(()->Mono.error(new DataNotFoundException("No data found in DB "))))
                ;

    }


    public Mono<Event> getEventById(String id) {
        log.debug("Service method in getEventById");
        return repository.findById(id)
                .switchIfEmpty(Mono.defer(()->Mono.error(new DataNotFoundException("No data found for id - "+id))))
                ;



    }

    public Mono<EventDto> save(Mono<Event> eventDtoMono){
        System.out.println("service method called ...");



        return  eventDtoMono
                //.map(AppUtils::dtoToEntity)
                .flatMap(repository::insert)

                .map(AppUtils::entityToDto);

    }

}
