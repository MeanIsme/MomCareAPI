package com.example.momcare.payload.request;

import com.example.momcare.models.SocialStory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserStoryNewRequest {
    private String userName;
    private String displayName;
    private String userId;
    private String avtUrl;
    private SocialStory socialStory;
}
