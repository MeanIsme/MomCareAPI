package com.example.momcare.repository;

import com.example.momcare.models.SocialPost;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SocialPostRepository extends MongoRepository<SocialPost, String> {
    List<SocialPost> getSocialPostsByUserId(String userId);
    SocialPost getSocialPostById(String id);
}
