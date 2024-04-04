package com.example.momcare.payload.request;

import com.example.momcare.models.Reaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialReactionNewRequest {
    private String postId;
    private String userId;
    private Reaction reaction;
}
