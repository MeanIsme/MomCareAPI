package com.example.momcare.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SocialCommentNewRequest {
    private String postId;
    private String userId;
    private String commentId;
    private String imageUrl;
    private String description;
}
