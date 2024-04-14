package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "SocialComment")
public class SocialComment {
    private String id;
    private String userId;
    private String postId;
    private String commentId;
    private String description;
    private String time;


    public SocialComment(String userId, String postId, String commentId, String description, String time) {
        this.userId = userId;
        this.postId = postId;
        this.commentId = commentId;
        this.description = description;
        this.time = time;
    }

}

