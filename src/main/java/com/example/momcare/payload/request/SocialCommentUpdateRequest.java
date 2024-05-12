package com.example.momcare.payload.request;

import com.example.momcare.models.SocialReaction;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class SocialCommentUpdateRequest {
    private String postId;
    private String id;
    private String userId;
    private String description;
    private SocialReaction reaction;
    private String userIdReaction;
}
