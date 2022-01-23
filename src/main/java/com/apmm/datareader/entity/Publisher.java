package com.apmm.datareader.entity;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "publisher")
@XmlAccessorType(XmlAccessType.FIELD)
public class Publisher {
    private Address address;
    private List<IdentifyingCodes> identifyingCodes;
    private String nmftaCode;
    private String partyName;
    private String publicKey;
    private String taxReference1;
    private String taxReference2;
}
