package com.example.momcare.payload.request;

import com.example.momcare.models.SocialStory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserStoryDeleteRequest {
    private String userId;
    private List<SocialStory> socialStories;
}
