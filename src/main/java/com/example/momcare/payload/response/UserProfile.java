package com.example.momcare.payload.response;

import com.example.momcare.models.SocialPost;
import com.example.momcare.models.UserStory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserProfile {
    private String id;
    private String username;
    private String displayName;
    private String avtUrl;
    private Set<String> followers;
    private List<SocialPost> posts;
    private UserStory story;
    private Set<String> share;
}
