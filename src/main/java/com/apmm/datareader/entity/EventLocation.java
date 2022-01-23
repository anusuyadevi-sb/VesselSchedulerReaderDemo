package com.apmm.datareader.entity;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "eventLocation")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventLocation {
    private String UNLocationCode;
    private Address address;
    private String latitude;
    private String locationName;
    private String longitude;
}
