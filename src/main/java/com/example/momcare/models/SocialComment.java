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
    private Map<String, SocialReaction> reactions;
    private List<String> replies;
    private String description;
    private String time;


    public SocialComment() {
    }

    public SocialComment(String userId, String postId, String commentId, String description, String time) {
        this.userId = userId;
        this.postId = postId;
        this.commentId = commentId;
        this.description = description;
        this.time = time;
        this.reactions = new HashMap<>();
    }

    public SocialComment(String id, String userId, String postId, String commentId, Map<String, SocialReaction> reactions, String description, String time) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.commentId = commentId;
        this.reactions = reactions;
        this.description = description;
        this.time = time;
    }

    public SocialComment(String id, String userId, String postId, String commentId, Map<String, SocialReaction> reactions, List<String> replies, String description, String time) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.commentId = commentId;
        this.reactions = reactions;
        this.replies = replies;
        this.description = description;
        this.time = time;
    }
}

