package com.apmm.datareader.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="events")
public class Event {
    @Id
    private String id;
    private String event_id;
    private String event_message;
    private String event_message_json;

}
