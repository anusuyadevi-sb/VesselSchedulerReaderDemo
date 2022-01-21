package com.apmm.datareader;

import com.apmm.datareader.controller.EventController;
import com.apmm.datareader.dto.EventDto;
import com.apmm.datareader.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(EventController.class)
class VesselSchedulerReaderApplicationTests {

	@Autowired
	private WebTestClient webTestClient;
	@MockBean
	private EventService service;

	/*@Test
	public void getEventByIdTest() {
		Mono<String> eventDtoMono = Mono.just("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <UNLocationCode>USNYC</UNLocationCode> <carrierServiceCode>FE1</carrierServiceCode> <carrierVoyageNumber>2103S</carrierVoyageNumber> <delayReasonCode>WEA</delayReasonCode> <eventClassifierCode>ACT</eventClassifierCode> <eventDateTime>2021-12-13T05:36:44.850Z</eventDateTime>");
		when(service.getEventById(any())).thenReturn(eventDtoMono);

		Flux<String> responseBody = webTestClient.get().uri("/event/61e42b936e38c9db5479b915")
				.exchange()
				.expectStatus().isOk()
				.returnResult(String.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(p -> p.equals("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <UNLocationCode>USNYC</UNLocationCode> <carrierServiceCode>FE1</carrierServiceCode> <carrierVoyageNumber>2103S</carrierVoyageNumber> <delayReasonCode>WEA</delayReasonCode> <eventClassifierCode>ACT</eventClassifierCode> <eventDateTime>2021-12-13T05:36:44.850Z</eventDateTime>"))
				.verifyComplete();
	}


	@Test
	public void getEventsTest() {
		Flux<String> eventDtoFlux = Flux.just((new String("Anu")),(new String("Kathir")),(new String ("Kavi")));

		when(service.getEvents()).thenReturn(eventDtoFlux);

		Flux<String> responseBody = webTestClient.get().uri("/event")
				.exchange()
				.expectStatus().isOk()
				.returnResult(String.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext("Anu")
				.expectNext("Kathir")
				.expectNext("Kavi")
				.verifyComplete();

	}*/

}