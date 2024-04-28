package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class SocialStory extends SocialPost{
    private String expiredTime;

    public SocialStory(String description, String userId, List<Media> media, String time, String expiredTime) {
        super(description, userId, media, time);
        this.expiredTime = expiredTime;
    }
}
