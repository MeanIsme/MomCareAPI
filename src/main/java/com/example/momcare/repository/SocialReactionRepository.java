package com.example.momcare.repository;

import com.example.momcare.models.SocialPost;
import com.example.momcare.models.SocialReaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SocialReactionRepository extends MongoRepository<SocialReaction,String> {
    SocialReaction getSocialReactionById(String id);
}
