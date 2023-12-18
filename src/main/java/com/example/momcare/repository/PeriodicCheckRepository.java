package com.example.momcare.repository;

import com.example.momcare.models.PeriodicCheck;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PeriodicCheckRepository extends MongoRepository<PeriodicCheck, String> {
    @Query("{weekFrom : ?0}")
    PeriodicCheck findByWeekFrom(int weekFrom);
}
