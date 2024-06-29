package com.example.momcare.payload.request;

import com.example.momcare.models.SocialReaction;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class SocialCommentUpdateRequest {
    private String postId;
    private String id;
    private String userId;
    private String description;
    private String imageUrl;
    private SocialReaction reaction;
    private String userIdReaction;
}
