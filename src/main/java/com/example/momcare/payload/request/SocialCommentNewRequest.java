package com.example.momcare.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SocialCommentNewRequest {
    private String postId;
    private String userId;
    private String userName;
    private String displayName;
    private String avtUrl;
    private String commentId;
    private String description;
}
