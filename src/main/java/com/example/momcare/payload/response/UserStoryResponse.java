package com.example.momcare.payload.response;

import com.example.momcare.models.SocialStory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class UserStoryResponse {
    private String id;
    private String userName;
    private String displayName;
    private String userId;
    private String avtUrl;
    private List<SocialStory> socialStories;
}
