package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Setter
@Document(collection = "SocialPost")
public class SocialPost {
    private String id;
    private String description;
    private String userId;
    private String userName;
    private String displayName;
    private String avtUrl;
    private Map<String, SocialReaction> reactions;
    private int countReactions;
    private Set<String> comments;
    private int countComments;
    private Set<String> share;
    private int countShare;
    private List<Media> media;
    private String time;

    public SocialPost() {
    }



    public SocialPost(String description, String userId,String userName,String displayName,String avtUrl, List<Media> media, String time) {
        this.description = description;
        this.userId = userId;
        this.userName = userName;
        this.displayName = displayName;
        this.avtUrl = avtUrl;
        this.media = Objects.requireNonNullElse(media, Collections.emptyList());
        this.time = time;
        this.reactions = Collections.emptyMap();
        this.comments = Collections.emptySet();
        this.share = Collections.emptySet();
    }


}
