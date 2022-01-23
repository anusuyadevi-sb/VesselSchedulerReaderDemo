package com.apmm.datareader.utils;


import com.apmm.datareader.entity.Event;

public class EventUtils {

    private static Event event;
    public static Event getEvent(){
        event=new Event();
        event.setId("123");
        event.setEventId("123");
        event.setEventMessage("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <UNLocationCode>USNYC</UNLocationCode> <carrierServiceCode>FE1</carrierServiceCode> <carrierVoyageNumber>2103S</carrierVoyageNumber> <delayReasonCode>WEA</delayReasonCode> <eventClassifierCode>ACT</eventClassifierCode> <eventDateTime>2021-12-13T05:36:44.850Z</eventDateTime>");
        return event;
    }
}
