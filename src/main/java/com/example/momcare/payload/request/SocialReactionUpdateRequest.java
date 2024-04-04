package com.example.momcare.payload.request;

import com.example.momcare.models.Reaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialReactionUpdateRequest {
    private String postId;
    private String id;
    private Reaction reaction;
}
