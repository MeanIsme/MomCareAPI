package com.example.momcare.repository;

import com.example.momcare.models.VideoCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoCategoryRepository extends MongoRepository<VideoCategory, String> {

}
