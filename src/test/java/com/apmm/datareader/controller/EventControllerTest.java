package com.apmm.datareader.controller;


import com.apmm.datareader.entity.Event;
import com.apmm.datareader.service.EventService;
import com.apmm.datareader.utils.AppUtils;
import com.apmm.datareader.utils.EventUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EventController.class)
@Import(EventService.class)
 class EventControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EventService service;

    @MockBean
    private AppUtils appUtils;


    @Test
     void getAllEvents(){
        Event event= EventUtils.getEvent();
        given(service.getEvents()).willReturn( Flux.just(event).map(AppUtils::entityToDto));

        webTestClient.get()
                .uri(builder->builder.path("/events").build()).exchange().expectStatus().isOk();

    }

    @Test
     void getEventById(){
        Event event= EventUtils.getEvent();
        given(service.getEventById("123")).willReturn( Mono.just(event).map(AppUtils::entityToDto));

        webTestClient.get()
                .uri(builder->builder.path("/event/{id}").build("123")).exchange().expectStatus().isOk();
    }

    @Test
     void getEventByIdNotAv(){
        Event event= EventUtils.getEvent();
        given(service.getEventById("")).willReturn( Mono.just(event).map(AppUtils::entityToDto));

        webTestClient.get()
                .uri(builder->builder.path("/event/{id}").build("")).exchange().expectStatus().isNotFound();
    }

    @Test
     void saveEventTest(){
        Event event= EventUtils.getEvent();
        given(service.save(Mono.just(event))).willReturn(Mono.just(event).map(AppUtils::entityToDto));

        webTestClient.post()
                .uri(builder -> builder.path("/").build()).exchange().expectStatus().isOk();
    }

    @Test
     void updateEventMethodNotAllowedTest(){
        Event event= EventUtils.getEvent();
        given(service.updateEvent(Mono.just(event).map(AppUtils::entityToDto),"123")).willReturn(Mono.just(event).map(AppUtils::entityToDto));

        webTestClient.post()
                .uri(builder -> builder.path("/event/update/{id}").build("123")).exchange().expectStatus().is4xxClientError();
    }

    @Test
     void updateEventTest(){
        Event event= EventUtils.getEvent();
        given(service.updateEvent(Mono.just(event).map(AppUtils::entityToDto),"123")).willReturn(Mono.just(event).map(AppUtils::entityToDto));

        webTestClient.put()
                .uri(builder -> builder.path("/event/update/{id}").build("123")).exchange().expectStatus().isOk();
    }

}
