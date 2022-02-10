package com.apmm.datareader.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {
    private String id;
    private String eventId;
    private String eventMessage;
    private String eventJson;

}
