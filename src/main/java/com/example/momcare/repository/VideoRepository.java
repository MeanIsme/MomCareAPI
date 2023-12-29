package com.example.momcare.repository;

import com.example.momcare.models.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface VideoRepository extends MongoRepository<Video, String> {
    @Query("{'category': ?0}")
    List<Video> findVideosByCategoryIn(String category);
}
