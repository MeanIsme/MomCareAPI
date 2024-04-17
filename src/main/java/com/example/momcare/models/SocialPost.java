package com.example.momcare.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Document(collection = "SocialPost")
public class SocialPost {
    private String id;
    private String description;
    private String userId;
    private Set<String> reactions;
    private int countReactions;
    private Set<String> comments;
    private int countComments;
    private Set<String> share;
    private int countShare;
    private List<Media> media;
    private String time;

    public SocialPost(String id, String description, String userId, Set<String> reactions, Set<String> comments, List<Media> media, String time) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.reactions = reactions;
        this.comments = comments;
        this.media = media;
        this.time = time;
    }

    public SocialPost(String description, String userId, List<Media> media, String time) {
        this.description = description;
        this.userId = userId;
        this.media = media;
        this.time = time;
    }
}
