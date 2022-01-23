package com.apmm.datareader.utils;

import com.apmm.datareader.dto.EventDto;
import com.apmm.datareader.entity.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppUtils {

    private AppUtils(){

    }
    public static EventDto entityToDto(Event event){
        log.info("Inside entityToDto");
        EventDto eventDto=new EventDto();
        BeanUtils.copyProperties(event,eventDto);
        return eventDto;
    }

    public static Event dtoToEntity(EventDto eventDto){
        log.info("Inside dtoToEntity");
        Event event=new Event();
        BeanUtils.copyProperties(eventDto,event);
        return event;
    }


}
