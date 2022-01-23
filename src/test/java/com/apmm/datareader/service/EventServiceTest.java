package com.apmm.datareader.service;

import com.apmm.datareader.entity.Event;
import com.apmm.datareader.exception.DataNotFoundException;
import com.apmm.datareader.repository.EventRepository;
import com.apmm.datareader.utils.AppUtils;
import com.apmm.datareader.utils.EventUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
@ContextConfiguration(classes={EventRepository.class})
@ExtendWith(SpringExtension.class)

 class EventServiceTest {

    private EventService service;

    @MockBean
    private  EventRepository repository;

    @MockBean
    private AppUtils appUtils;

    @BeforeEach
    void setUp() {
        this.service = new EventService(repository,appUtils);
    }

@Test
    public void getAllEvents(){
        Event event= EventUtils.getEvent();
        given(repository.findAll()).willReturn(Flux.just(event));

        StepVerifier.create(
                service.getEvents())
                .expectNextMatches(evt->{
                then(evt.getId()).isEqualTo("123");
                return true;
        }).expectNextCount(0).expectComplete().verify();

    }


    @Test
     void getAllEventsById(){
        Event event= EventUtils.getEvent();
        given(repository.findByEventId("123")).willReturn(Mono.just(event));

        StepVerifier.create(
                service.getEventById("123")).expectNextMatches(evt->{
            then(evt.getId()).isEqualTo("123");
            return true;
        }).expectNextCount(0).expectComplete().verify();

    }

    @Test
     void getAllEventsNodata(){
        Event event= EventUtils.getEvent();
        given(repository.findAll()).willReturn(Flux.empty());

        StepVerifier.create(
                service.getEvents())

                .expectError(DataNotFoundException.class).verify();


    }

    @Test
     void getAllEventsNodataById(){
        Event event= EventUtils.getEvent();
        given(repository.findByEventId("123")).willReturn(Mono.empty());

        StepVerifier.create(
                        service.getEventById("123"))
                .expectError(DataNotFoundException.class).verify();


    }

    @Test
     void saveEventsTest(){
        Event event= EventUtils.getEvent();
        given(repository.insert(event)).willReturn(Mono.just(event));

        StepVerifier.create(
                service.save(Mono.just(event))
        ).expectNextMatches(evt->{
            then(evt.getId()).isEqualTo("123");
            return true;
        }).expectNextCount(0).expectComplete().verify();
    }

    @Test
     void updateEventsTest(){
        Event event= EventUtils.getEvent();
        given(repository.findById("123")).willReturn(Mono.just(event));

        StepVerifier.create(
                        service.updateEvent(Mono.just(event).map(AppUtils::entityToDto),"123"))
                .expectComplete();
    }
}
