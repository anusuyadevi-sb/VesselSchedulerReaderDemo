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
@XmlRootElement(name = "identifyingCodes")
@XmlAccessorType(XmlAccessType.FIELD)
public class IdentifyingCodes {
    private String identifyingCode;
}


