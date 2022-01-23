package com.apmm.datareader.repository;

import com.apmm.datareader.entity.Event;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EventRepository extends ReactiveMongoRepository<Event,String> {

    @Query("{'event_id' : ?0}")
    Mono<Event> findByEventId(String eventId);
}
