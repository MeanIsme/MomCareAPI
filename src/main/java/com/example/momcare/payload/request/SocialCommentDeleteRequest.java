package com.example.momcare.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialCommentDeleteRequest {
    private String postId;
    private String id;

}
