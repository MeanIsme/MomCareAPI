package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Document(collection = "UserStory")
public class UserStory {
    @Id
    private String id;
    private String userName;
    private String displayName;
    private String userId;
    private String avtUrl;
    private List<SocialStory> socialStories;

    public UserStory() {
    }

    public UserStory(String userName, String displayName, String userId, String avtUrl, List<SocialStory> socialStories) {
        this.userName = userName;
        this.displayName = displayName;
        this.userId = userId;
        this.avtUrl = avtUrl;
        this.socialStories = socialStories;
    }

    public UserStory(String userName, String displayName, String userId, String avtUrl, SocialStory socialStory) {
        this.userName = userName;
        this.displayName = displayName;
        this.userId = userId;
        this.avtUrl = avtUrl;
        this.socialStories = new ArrayList<>();
        socialStory.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        this.socialStories.add(socialStory);
    }

    public UserStory(String id, String userName, String displayName, String userId, String avtUrl, List<SocialStory> socialStories, SocialStory socialStory) {
        this.id = id;
        this.userName = userName;
        this.displayName = displayName;
        this.userId = userId;
        this.avtUrl = avtUrl;
        socialStory.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        socialStories.add(socialStory);
        this.socialStories = socialStories;
    }

    public UserStory(String id, String userName, String displayName, String userId, String avtUrl, List<SocialStory> socialStories) {
        this.id = id;
        this.userName = userName;
        this.displayName = displayName;
        this.userId = userId;
        this.avtUrl = avtUrl;
        this.socialStories = socialStories;
    }
}
