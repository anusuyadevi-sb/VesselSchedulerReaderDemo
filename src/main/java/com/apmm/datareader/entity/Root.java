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
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Root {
    private String UNLocationCode;
    private String carrierServiceCode;
    private String carrierVoyageNumber;
    private String delayReasonCode;
    private String eventClassifierCode;
    private String eventDateTime;
    private EventLocation eventLocation;
    private FacilitySMDGCode facilitySMDGCode;
    private String facilityTypeCode;
    private String modeOfTransport;
    private String operationsEventTypeCode;
    private String portCallServiceTypeCode;
    private Publisher publisher;
    private String publisherRole;
    private String remark;
    private TimestampId timestampId;
    private String transportCallSequenceNumber;
    private String vesselIMONumber;
    private VesselPosition vesselPosition;
}
