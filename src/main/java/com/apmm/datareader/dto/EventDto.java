package com.apmm.datareader.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private String id;
    private String event_id;
    private String event_message;
    private String event_message_json;

}
