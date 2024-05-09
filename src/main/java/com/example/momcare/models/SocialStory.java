package com.example.momcare.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Document(collection = "SocialStory")
public class SocialStory{
    @Id
    private String id;
    private String description;
    private String userId;
    private List<String> media;
    private String time;
    private String expiredTime;
    private String avtUrl;
    private int count;


    public SocialStory() {
    }

    public SocialStory(String description, String userId, List<String> media, String time, String expiredTime) {
        this.description = description;
        this.userId = userId;
        this.media = media;
        this.time = time;
        this.expiredTime = expiredTime;
        this.avtUrl = "";
        this.count = 0;
    }

    public SocialStory(String id, String description, String userId, List<String> media, String time, String expiredTime, String avtUrl, int count) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.media = media;
        this.time = time;
        this.expiredTime = expiredTime;
        this.avtUrl = avtUrl;
        this.count = count;
    }
}
