package com.apmm.datareader.controller;

import com.apmm.datareader.dto.EventDto;
import com.apmm.datareader.entity.Event;

//import com.apmm.datareader.exception.GlobalErrorAttributes;
//import com.apmm.datareader.exception.GlobalErrorWebExceptionHandler;
import com.apmm.datareader.service.EventService;
import com.apmm.datareader.utils.EventUtils;
import org.junit.Before;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EventController.class)
@Import(EventService.class)
public class EventControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EventService service;


@Test
public void getAllEvents(){
    Event event= EventUtils.getEvent();
    given(service.getEvents()).willReturn( Flux.just(event));

    webTestClient.get()
            .uri(builder->builder.path("/event").build()).exchange().expectStatus().isOk();

}
@Test
public void getEventById(){
    Event event= EventUtils.getEvent();
    given(service.getEventById("123")).willReturn( Mono.just(event));

    webTestClient.get()
            .uri(builder->builder.path("/event/{id}").build("123")).exchange().expectStatus().isOk();
}
    @Test
    public void getEventByIdNotAv(){
        Event event= EventUtils.getEvent();
        given(service.getEventById("")).willReturn( Mono.just(event));

        webTestClient.get()
                .uri(builder->builder.path("/event/{id}").build("")).exchange().expectStatus().isOk();
    }
}
