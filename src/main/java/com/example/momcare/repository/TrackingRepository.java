package com.example.momcare.repository;

import com.example.momcare.models.Tracking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TrackingRepository extends MongoRepository<Tracking,String> {
    @Query("{week : ?0}")
    Tracking findTrackingByWeek(int week);
    

}
