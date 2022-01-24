package com.apmm.datareader.service;

import com.apmm.datareader.dto.EventDto;
import com.apmm.datareader.entity.Event;
import com.apmm.datareader.entity.Root;
import com.apmm.datareader.exception.DataNotFoundException;
import com.apmm.datareader.repository.EventRepository;
import com.apmm.datareader.utils.AppUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Slf4j
@Service
@AllArgsConstructor
public class EventService {
    @Autowired
    private EventRepository repository;
    @Autowired
    private AppUtils appUtils;

    public Flux<EventDto> getEvents() {
        log.info("Inside getEvents");
        return repository.findAll()
                .switchIfEmpty(Mono.defer(()-> Mono.error(new DataNotFoundException("No data found in DB "))))
                .doOnNext(this::convertXmlToJson)
                .map(AppUtils::entityToDto);
    }

    public Mono<EventDto> getEventById(String id) {
        log.info("Inside getEventById || Id:: " +id);
        return repository.findByEventId(id)
                .switchIfEmpty(Mono.defer(()->Mono.error(new DataNotFoundException("No data found for id - "+id))))
                .doOnNext(this::convertXmlToJson)
                .map(AppUtils::entityToDto);
    }

    public Mono<EventDto> save(Mono<Event> eventDtoMono){
        log.info("service method called ...");
        return  eventDtoMono
                .flatMap(repository::insert)
                .map(AppUtils::entityToDto);

    }

    public Mono<EventDto> updateEvent(Mono<EventDto> eventDtoMono, String id){
        log.info("Inside updateEvent "+id);
        return repository.findById(id)
                .flatMap(e->eventDtoMono.map(AppUtils::dtoToEntity)
                        .doOnNext(p->p.setId(id)))
                .flatMap(repository::save)
                .map(AppUtils::entityToDto);
    }

    public  Mono<EventDto> convertXmlToJson(Event data){

        log.info("Inside convertXmlToJson || Id:: "+ data.getId());
        String result = null;
        String xmlData = data.getEventMessage();

        if ( StringUtil.isNullOrEmpty(data.getEventJson())) {
            log.info("JSON data is null or empty || Id:: "+ data.getId());
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Root.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                Root obj = (Root) unmarshaller.unmarshal(new StringReader(xmlData));
                ObjectMapper mapper = new ObjectMapper();
                result = mapper.writeValueAsString(obj);
                log.debug("converted the xml to JSON : : " + result);
                data.setEventJson(result);
            }
            catch (Exception e) {
                log.error(e.getLocalizedMessage());
            }

        }

        return Mono.just(data).map(AppUtils::entityToDto);
    }

}
