package com.apmm.datareader.repository;

import com.apmm.datareader.entity.Event;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends ReactiveMongoRepository<Event,String> {
}
