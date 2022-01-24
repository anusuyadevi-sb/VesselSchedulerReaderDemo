package com.apmm.datareader.utils;


import com.apmm.datareader.dto.EventDto;
import com.apmm.datareader.entity.Event;

public class EventUtils {

    private static Event event;
    private static EventDto eventDto;
    public static Event getEvent(){
        String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><UNLocationCode>USNYC</UNLocationCode><carrierServiceCode>FE1</carrierServiceCode><carrierVoyageNumber>2103S</carrierVoyageNumber><delayReasonCode>WEA</delayReasonCode><eventClassifierCode>ACT</eventClassifierCode><eventDateTime>2021-12-13T05:36:44.850Z</eventDateTime><eventLocation><UNLocationCode>USNYC</UNLocationCode><address><city>København</city><country>Denmark</country><floor>5. sal</floor><name>Henrik</name><postCode>1306</postCode><stateRegion>N/A</stateRegion><street>Kronprincessegade</street><streetNumber>54</streetNumber></address><latitude>48.8585500</latitude><locationName>Eiffel Tower</locationName><longitude>2.294492036</longitude></eventLocation><facilityTypeCode>BRTH</facilityTypeCode><modeOfTransport>BARGE</modeOfTransport><operationsEventTypeCode>ARRI</operationsEventTypeCode><portCallServiceTypeCode>BUNK</portCallServiceTypeCode><publisher><address><city>København</city><country>Denmark</country><floor>5. sal</floor><name>Henrik</name><postCode>1306</postCode><stateRegion>N/A</stateRegion><street>Kronprincessegade</street><streetNumber>54</streetNumber></address><nmftaCode>string</nmftaCode><partyName>Asseco Denmark</partyName><publicKey>eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkFzaW</publicKey><taxReference1>CVR-25645774</taxReference1><taxReference2>CVR-25645774</taxReference2></publisher><publisherRole>AG</publisherRole><remark>Port closed due to strike</remark><timestampId><timestamp>0</timestamp></timestampId><transportCallSequenceNumber>2</transportCallSequenceNumber><vesselIMONumber>9321483</vesselIMONumber><vesselPosition><latitude>48.8585500</latitude><longitude>2.294492036</longitude></vesselPosition></root>";

        event=new Event();
        event.setId("123");
        event.setEventId("123");
        event.setEventMessage(xmlData);
        return event;
    }

    public static EventDto getEventDto(){
        String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><UNLocationCode>USNYC</UNLocationCode><carrierServiceCode>FE1</carrierServiceCode><carrierVoyageNumber>2103S</carrierVoyageNumber><delayReasonCode>WEA</delayReasonCode><eventClassifierCode>ACT</eventClassifierCode><eventDateTime>2021-12-13T05:36:44.850Z</eventDateTime><eventLocation><UNLocationCode>USNYC</UNLocationCode><address><city>København</city><country>Denmark</country><floor>5. sal</floor><name>Henrik</name><postCode>1306</postCode><stateRegion>N/A</stateRegion><street>Kronprincessegade</street><streetNumber>54</streetNumber></address><latitude>48.8585500</latitude><locationName>Eiffel Tower</locationName><longitude>2.294492036</longitude></eventLocation><facilityTypeCode>BRTH</facilityTypeCode><modeOfTransport>BARGE</modeOfTransport><operationsEventTypeCode>ARRI</operationsEventTypeCode><portCallServiceTypeCode>BUNK</portCallServiceTypeCode><publisher><address><city>København</city><country>Denmark</country><floor>5. sal</floor><name>Henrik</name><postCode>1306</postCode><stateRegion>N/A</stateRegion><street>Kronprincessegade</street><streetNumber>54</streetNumber></address><nmftaCode>string</nmftaCode><partyName>Asseco Denmark</partyName><publicKey>eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkFzaW</publicKey><taxReference1>CVR-25645774</taxReference1><taxReference2>CVR-25645774</taxReference2></publisher><publisherRole>AG</publisherRole><remark>Port closed due to strike</remark><timestampId><timestamp>0</timestamp></timestampId><transportCallSequenceNumber>2</transportCallSequenceNumber><vesselIMONumber>9321483</vesselIMONumber><vesselPosition><latitude>48.8585500</latitude><longitude>2.294492036</longitude></vesselPosition></root>";

        eventDto=new EventDto();
        eventDto.setId("123");
        eventDto.setEventId("123");
        eventDto.setEventMessage(xmlData);
        return eventDto;
    }
}
