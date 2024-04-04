package com.example.momcare.repository;

import com.example.momcare.models.SocialComment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SocialCommentRepository extends MongoRepository<SocialComment,String> {

    SocialComment getSocialCommentById(String id);

}
