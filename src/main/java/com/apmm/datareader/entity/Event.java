package com.apmm.datareader.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="events")
@Builder
public class Event {
    @Id
    private String id;
    private String eventId;
    private String eventMessage;
    private String eventJson;

}
