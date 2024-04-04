package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter

@Document(collection = "SocialPost")
public class SocialPost {
    private String id;
    private String description;
    private String userId;
    Set<String> reactions;
    Set<String> comments;
    Set<String> images;
    private String time;

    public SocialPost(String description, String userId, Set<String> images, String time) {
        this.description = description;
        this.userId = userId;
        this.images = images;
        this.time = time;
    }
}
