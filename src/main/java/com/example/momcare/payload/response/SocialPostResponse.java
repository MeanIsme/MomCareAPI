package com.example.momcare.payload.response;

import com.example.momcare.models.Media;
import com.example.momcare.models.SocialReaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialPostResponse {
    private String id;
    private String description;
    private String userId;
    private String userName;
    private String displayName;
    private String avtUrl;
    private Map<String, SocialReaction> reactions;
    private Set<String> comments;
    private Set<String> share;
    private List<Media> media;
    private String time;

}
