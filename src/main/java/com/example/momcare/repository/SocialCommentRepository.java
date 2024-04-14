package com.example.momcare.repository;

import com.example.momcare.models.SocialComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SocialCommentRepository extends MongoRepository<SocialComment,String> {

    SocialComment getSocialCommentById(String id);
    @Query("{'postId': ?0}")
    List<SocialComment> findSocialCommentByPostId(String postId);

}
