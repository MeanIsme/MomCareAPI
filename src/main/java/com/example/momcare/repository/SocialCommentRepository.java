package com.example.momcare.repository;

import com.example.momcare.models.SocialComment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SocialCommentRepository extends MongoRepository<SocialComment,String> {

    @Query("{'postId': ?0}")
    List<SocialComment> findSocialCommentByPostId(String postId);
    @Query("{'_id': ?0}")
    SocialComment findSocialCommentById(ObjectId id);

}
