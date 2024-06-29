package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Setter
@Document(collection = "SocialComment")
public class SocialComment {
    private String id;
    private String userId;
    private String postId;
    private String commentId;
    private String imageUrl;
    private Map<String, SocialReaction> reactions;
    private List<String> replies;
    private String description;
    private String time;


    public SocialComment() {
    }





    public SocialComment(String userId, String postId, String commentId,String imageUrl, String description, String time) {
        this.userId = userId;
        this.postId = postId;
        this.commentId = commentId;
        this.reactions = new HashMap<>();
        this.replies = new ArrayList<>();
        this.imageUrl = imageUrl;
        this.description = description;
        this.time = time;
    }
}

