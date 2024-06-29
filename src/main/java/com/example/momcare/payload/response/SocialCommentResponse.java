package com.example.momcare.payload.response;

import com.example.momcare.models.SocialReaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialCommentResponse {
    private String id;
    private String userId;
    private String userName;
    private String displayName;
    private String avtUrl;
    private String postId;
    private String commentId;
    private String imageUrl;
    private Map<String, SocialReaction> reactions;
    private List<String> replies;
    private String description;
    private String time;
}
