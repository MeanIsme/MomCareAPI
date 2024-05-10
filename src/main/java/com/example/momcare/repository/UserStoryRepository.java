package com.example.momcare.repository;

import com.example.momcare.models.UserStory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserStoryRepository extends MongoRepository<UserStory, String> {
    UserStory findByUserId(String userId);
}
