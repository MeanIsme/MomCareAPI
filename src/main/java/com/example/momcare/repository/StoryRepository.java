package com.example.momcare.repository;

import com.example.momcare.models.SocialStory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StoryRepository extends MongoRepository<SocialStory, String> {

    List<SocialStory> getSocialStoriesByUserId(String id);
    List<SocialStory> getById(String id);
}
