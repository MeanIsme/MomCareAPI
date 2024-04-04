package com.example.momcare.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SocialCommentUpdateRequest {
    private String postId;
    private String id;
    private String userId;
    private String description;
}
