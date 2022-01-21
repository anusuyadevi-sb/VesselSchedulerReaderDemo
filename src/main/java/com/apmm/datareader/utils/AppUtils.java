package com.apmm.datareader.utils;

import com.apmm.datareader.dto.EventDto;
import com.apmm.datareader.entity.Event;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static EventDto entityToDto(Event event){
        EventDto eventDto=new EventDto();
        BeanUtils.copyProperties(event,eventDto);
        return eventDto;
    }

    public static Event dtoToEntity(EventDto eventDto){
        Event event=new Event();
        BeanUtils.copyProperties(eventDto,event);
        return event;
    }


}
