package com.example.momcare.repository;

import com.example.momcare.models.SocialPost;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SocialPostRepository extends MongoRepository<SocialPost, String> {
    List<SocialPost> getSocialPostsByUserId(String userId);

    Page<SocialPost> getSocialPostsByUserId(String userId, Pageable pageable);
    SocialPost getSocialPostById(String id);
}
