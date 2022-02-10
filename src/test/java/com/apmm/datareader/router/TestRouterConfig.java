package com.apmm.datareader.router;


import com.apmm.datareader.dto.EventDto;
import com.apmm.datareader.entity.Event;
import com.apmm.datareader.handler.EventHandler;
import com.apmm.datareader.repository.EventRepository;
import com.apmm.datareader.service.EventService;
import com.apmm.datareader.utils.AppUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RouterConfig.class, EventHandler.class, EventService.class})
@WebFluxTest
public class TestRouterConfig {

    @Autowired
    private ApplicationContext context;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private AppUtils appUtils;

    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    public void testGetEventByEventId() {
        Event event = Event.builder()
                        .eventId("1")
                        .id("ABC123")
                        .eventJson("JSON Data")
                        .eventMessage("XML Data")
                        .build();

        Mono<Event> eventMono = Mono.just(event);

        given(eventRepository.findByEventId("1")).willReturn(eventMono);

        webTestClient.get()
                .uri("/router/event/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EventDto.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.getId()).isEqualTo("ABC123");
                            Assertions.assertThat(userResponse.getEventId()).isEqualTo("1");
                            Assertions.assertThat(userResponse.getEventJson()).isEqualTo("JSON Data");
                            Assertions.assertThat(userResponse.getEventMessage()).isEqualTo("XML Data");
                        }
                );
    }

    @Test
    public void testGetEventByEventId_NegOne() {
        Event event = Event.builder()
                .eventId("1")
                .id("ABC123")
                .eventJson("")
                .eventMessage("XML Data Error Format")
                .build();

        Mono<Event> eventMono = Mono.just(event);

        given(eventRepository.findByEventId("1")).willReturn(eventMono);
        given(eventRepository.save(any())).willReturn(eventMono);

        webTestClient.get()
                .uri("/router/event/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EventDto.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.getId()).isEqualTo("ABC123");
                            Assertions.assertThat(userResponse.getEventId()).isEqualTo("1");
                            Assertions.assertThat(userResponse.getEventJson()).isEmpty();
                            Assertions.assertThat(userResponse.getEventMessage()).isEqualTo("XML Data Error Format");
                        }
                );
    }

    @Test
    public void testGetEventByEventId_Neg() {

        String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><UNLocationCode>USNYC</UNLocationCode><carrierServiceCode>FE1</carrierServiceCode><carrierVoyageNumber>2103S</carrierVoyageNumber><delayReasonCode>WEA</delayReasonCode><eventClassifierCode>ACT</eventClassifierCode><eventDateTime>2021-12-13T05:36:44.850Z</eventDateTime><eventLocation><UNLocationCode>USNYC</UNLocationCode><address><city>København</city><country>Denmark</country><floor>5. sal</floor><name>Henrik</name><postCode>1306</postCode><stateRegion>N/A</stateRegion><street>Kronprincessegade</street><streetNumber>54</streetNumber></address><latitude>48.8585500</latitude><locationName>Eiffel Tower</locationName><longitude>2.294492036</longitude></eventLocation><facilityTypeCode>BRTH</facilityTypeCode><modeOfTransport>BARGE</modeOfTransport><operationsEventTypeCode>ARRI</operationsEventTypeCode><portCallServiceTypeCode>BUNK</portCallServiceTypeCode><publisher><address><city>København</city><country>Denmark</country><floor>5. sal</floor><name>Henrik</name><postCode>1306</postCode><stateRegion>N/A</stateRegion><street>Kronprincessegade</street><streetNumber>54</streetNumber></address><nmftaCode>string</nmftaCode><partyName>Asseco Denmark</partyName><publicKey>eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkFzaW</publicKey><taxReference1>CVR-25645774</taxReference1><taxReference2>CVR-25645774</taxReference2></publisher><publisherRole>AG</publisherRole><remark>Port closed due to strike</remark><timestampId><timestamp>0</timestamp></timestampId><transportCallSequenceNumber>2</transportCallSequenceNumber><vesselIMONumber>9321483</vesselIMONumber><vesselPosition><latitude>48.8585500</latitude><longitude>2.294492036</longitude></vesselPosition></root>";
        Event event = Event.builder()
                .eventId("10")
                .id("ABC123456789")
                .eventJson("")
                .eventMessage(xmlData)
                .build();

        Mono<Event> eventMono = Mono.just(event);

        given(eventRepository.findByEventId("10")).willReturn(eventMono);
        given(eventRepository.save(any())).willReturn(eventMono);

        webTestClient.get()
                .uri("/router/event/10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EventDto.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.getId()).isEqualTo("ABC123456789");
                            Assertions.assertThat(userResponse.getEventId()).isEqualTo("10");
                            Assertions.assertThat(userResponse.getEventJson()).isNotEmpty();
                            Assertions.assertThat(userResponse.getEventMessage()).isEqualTo(xmlData);
                        }
                );
    }

    @Test
    public void testGetEventByEventId_DataNotFound() {

        given(eventRepository.findByEventId("102")).willReturn(Mono.empty());
        webTestClient.get()
                .uri("/router/event/102")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testGetEvents() {

        Event eventOne = Event.builder()
                .eventId("1")
                .id("ABC123")
                .eventJson("JSON Data One")
                .eventMessage("XML Data One")
                .build();

        Event eventTwo = Event.builder()
                .eventId("2")
                .id("DEF123")
                .eventJson("JSON Data Two")
                .eventMessage("XML Data Two")
                .build();


        given(eventRepository.findAll()).willReturn(Flux.just(eventOne, eventTwo));

        webTestClient.get()
                .uri("/router/events")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EventDto.class)
                .value(userResponse -> {
                    Assertions.assertThat(userResponse.get(0).getId()).isEqualTo("ABC123");
                    Assertions.assertThat(userResponse.get(0).getEventId()).isEqualTo("1");
                    Assertions.assertThat(userResponse.get(0).getEventJson()).isEqualTo("JSON Data One");
                    Assertions.assertThat(userResponse.get(0).getEventMessage()).isEqualTo("XML Data One");

                    Assertions.assertThat(userResponse.get(1).getId()).isEqualTo("DEF123");
                    Assertions.assertThat(userResponse.get(1).getEventId()).isEqualTo("2");
                    Assertions.assertThat(userResponse.get(1).getEventJson()).isEqualTo("JSON Data Two");
                    Assertions.assertThat(userResponse.get(1).getEventMessage()).isEqualTo("XML Data Two");
                        }
                );
    }

    @Test
    public void testCreateEvent() {

        String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><UNLocationCode>USNYC</UNLocationCode><carrierServiceCode>FE1</carrierServiceCode><carrierVoyageNumber>2103S</carrierVoyageNumber><delayReasonCode>WEA</delayReasonCode><eventClassifierCode>ACT</eventClassifierCode><eventDateTime>2021-12-13T05:36:44.850Z</eventDateTime><eventLocation><UNLocationCode>USNYC</UNLocationCode><address><city>København</city><country>Denmark</country><floor>5. sal</floor><name>Henrik</name><postCode>1306</postCode><stateRegion>N/A</stateRegion><street>Kronprincessegade</street><streetNumber>54</streetNumber></address><latitude>48.8585500</latitude><locationName>Eiffel Tower</locationName><longitude>2.294492036</longitude></eventLocation><facilityTypeCode>BRTH</facilityTypeCode><modeOfTransport>BARGE</modeOfTransport><operationsEventTypeCode>ARRI</operationsEventTypeCode><portCallServiceTypeCode>BUNK</portCallServiceTypeCode><publisher><address><city>København</city><country>Denmark</country><floor>5. sal</floor><name>Henrik</name><postCode>1306</postCode><stateRegion>N/A</stateRegion><street>Kronprincessegade</street><streetNumber>54</streetNumber></address><nmftaCode>string</nmftaCode><partyName>Asseco Denmark</partyName><publicKey>eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkFzaW</publicKey><taxReference1>CVR-25645774</taxReference1><taxReference2>CVR-25645774</taxReference2></publisher><publisherRole>AG</publisherRole><remark>Port closed due to strike</remark><timestampId><timestamp>0</timestamp></timestampId><transportCallSequenceNumber>2</transportCallSequenceNumber><vesselIMONumber>9321483</vesselIMONumber><vesselPosition><latitude>48.8585500</latitude><longitude>2.294492036</longitude></vesselPosition></root>";
        Event event = Event.builder()
                .eventId("100")
                .id("XYZ123")
                .eventMessage(xmlData)
                .build();

        Mono<Event> eventMono = Mono.just(event);
        given(eventRepository.insert((Event) any())).willReturn(eventMono);

        webTestClient.post()
                .uri("/router/event/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(event), Event.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EventDto.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.getId()).isEqualTo("XYZ123");
                            Assertions.assertThat(userResponse.getEventMessage()).isEqualTo(xmlData);
                            Assertions.assertThat(userResponse.getEventId()).isEqualTo("100");
                            Assertions.assertThat(userResponse.getEventJson()).isNull();
                        }
                );
    }

    @Test
    public void testUpdateEventByEventId() {

        String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><UNLocationCode>USNYC</UNLocationCode><carrierServiceCode>FE1</carrierServiceCode><carrierVoyageNumber>2103S</carrierVoyageNumber><delayReasonCode>WEA</delayReasonCode><eventClassifierCode>ACT</eventClassifierCode><eventDateTime>2021-12-13T05:36:44.850Z</eventDateTime><eventLocation><UNLocationCode>USNYC</UNLocationCode><address><city>København</city><country>Denmark</country><floor>5. sal</floor><name>Henrik</name><postCode>1306</postCode><stateRegion>N/A</stateRegion><street>Kronprincessegade</street><streetNumber>54</streetNumber></address><latitude>48.8585500</latitude><locationName>Eiffel Tower</locationName><longitude>2.294492036</longitude></eventLocation><facilityTypeCode>BRTH</facilityTypeCode><modeOfTransport>BARGE</modeOfTransport><operationsEventTypeCode>ARRI</operationsEventTypeCode><portCallServiceTypeCode>BUNK</portCallServiceTypeCode><publisher><address><city>København</city><country>Denmark</country><floor>5. sal</floor><name>Henrik</name><postCode>1306</postCode><stateRegion>N/A</stateRegion><street>Kronprincessegade</street><streetNumber>54</streetNumber></address><nmftaCode>string</nmftaCode><partyName>Asseco Denmark</partyName><publicKey>eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkFzaW</publicKey><taxReference1>CVR-25645774</taxReference1><taxReference2>CVR-25645774</taxReference2></publisher><publisherRole>AG</publisherRole><remark>Port closed due to strike</remark><timestampId><timestamp>0</timestamp></timestampId><transportCallSequenceNumber>2</transportCallSequenceNumber><vesselIMONumber>9321483</vesselIMONumber><vesselPosition><latitude>48.8585500</latitude><longitude>2.294492036</longitude></vesselPosition></root>";
        Event event = Event.builder()
                .eventId("200")
                .id("A1B2C3")
                .eventMessage(xmlData)
                .build();
        Mono<Event> eventMono = Mono.just(event);

        Event eventReq = Event.builder()
                .eventId("201")
                .id("A1B2C3")
                .eventMessage(xmlData)
                .eventJson("JSON Data New")
                .build();
        Mono<Event> eventReqMono = Mono.just(eventReq);


        given(eventRepository.findByEventId("200")).willReturn(eventMono);

        given(eventRepository.save(any())).willReturn(eventReqMono);

        webTestClient.put()
                .uri("/router/event/update/200")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(eventReq).map(AppUtils::entityToDto), EventDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EventDto.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.getId()).isEqualTo("A1B2C3");
                            Assertions.assertThat(userResponse.getEventMessage()).isEqualTo(xmlData);
                            Assertions.assertThat(userResponse.getEventId()).isEqualTo("201");
                            Assertions.assertThat(userResponse.getEventJson()).isEqualTo("JSON Data New");
                        }
                );
    }
}
