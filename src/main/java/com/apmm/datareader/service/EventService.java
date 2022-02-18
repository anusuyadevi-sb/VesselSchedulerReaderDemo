package com.apmm.datareader.service;

import com.apmm.datareader.dto.EventDto;
import com.apmm.datareader.entity.Event;
import com.apmm.datareader.entity.Root;
import com.apmm.datareader.exception.BadRequestException;
import com.apmm.datareader.exception.DataNotFoundException;
import com.apmm.datareader.repository.EventRepository;
import com.apmm.datareader.utils.AppUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;

    public Flux<EventDto> getEvents() {
        log.info("Inside getEvents");
        return repository.findAll()
                .onErrorResume( Mono::error)
                .switchIfEmpty(Mono.defer(()-> Mono.error(new DataNotFoundException(HttpStatus.NOT_FOUND,"No data found in DB"))))
                .flatMap(event->{
                    if (StringUtil.isNullOrEmpty(event.getEventJson())){
                        return repository.save(this.convertXmlToJson(event));
                    }else
                    {
                        return Mono.just(event);
                    }
                })
                .map(AppUtils::entityToDto);
    }

    public Mono<EventDto> save(Mono<Event> eventDtoMono){
        log.info("service method called ...");
        return  eventDtoMono
                .flatMap(repository::insert)
                .map(AppUtils::entityToDto);

    }

    public Mono<EventDto> updateEventByEventId(Mono<EventDto> eventDtoMono, String id){
        log.info("Inside updateEventByEventId "+id);
        return repository.findByEventId(id)
                .flatMap(e->eventDtoMono.map(AppUtils::dtoToEntity)
                        .doOnNext(p->p.setId(e.getId())))
                .flatMap(repository::save)
                .map(AppUtils::entityToDto);
    }

    public Mono<EventDto> getEventById(String id) {
        log.info("Inside getEventById || Id:: " +id);
        if(id != null && !id.matches("^[a-zA-Z0-9]*$")){
            throw new BadRequestException(HttpStatus.BAD_REQUEST,"Bad request..");
        }
        return repository.findByEventId(id)
                .onErrorResume( Mono::error)
                .switchIfEmpty(Mono.defer(()->Mono.error(new DataNotFoundException(HttpStatus.NOT_FOUND,"No data found for id - "+id))))
                .flatMap(event->{
                    if (StringUtil.isNullOrEmpty(event.getEventJson())){
                       return repository.save(this.convertXmlToJson(event));
                    }else
                    {
                        return Mono.just(event);
                    }
                })
                .map(AppUtils::entityToDto);
    }

    public  Event convertXmlToJson(Event data){
        log.info("Inside convertXmlToJson || Id:: "+ data.getId());
        String result = null;
        String xmlData = data.getEventMessage();
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Root.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                Root obj = (Root) unmarshaller.unmarshal(new StringReader(xmlData));
                ObjectMapper mapper = new ObjectMapper();
                result = mapper.writeValueAsString(obj);
                data.setEventJson(result);
            }
            catch (Exception e) {
                log.error(e.getLocalizedMessage());
            }
        return data;
    }
}
