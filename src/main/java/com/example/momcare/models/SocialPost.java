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

    public SocialPost(String id, String description, String userId, Map<String, SocialReaction> reactions, Set<String> comments, List<Media> media, String time) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.reactions = reactions;
        this.comments = comments;
        this.media = media;
        this.time = time;
    }

    public SocialPost(String id, String description, String userId, int countReactions, int countComments, int countShare, String time) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.countReactions = countReactions;
        this.countComments = countComments;
        this.countShare = countShare;
        this.time = time;
    }

    public SocialPost(String description, String userId, Map<String, SocialReaction>  reactions, int countReactions, Set<String> comments, int countComments, Set<String> share, int countShare, List<Media> media, String time) {
        this.description = description;
        this.userId = userId;
        this.reactions = reactions;
        this.countReactions = countReactions;
        this.comments = comments;
        this.countComments = countComments;
        this.share = share;
        this.countShare = countShare;
        this.media = media;
        this.time = time;
    }

    public SocialPost(String description, String userId, List<Media> media, String time) {
        this.description = description;
        this.userId = userId;
        this.media = Objects.requireNonNullElse(media, Collections.emptyList());
        this.time = time;
        this.reactions = Collections.emptyMap();
        this.comments = Collections.emptySet();
        this.share = Collections.emptySet();
    }
}
