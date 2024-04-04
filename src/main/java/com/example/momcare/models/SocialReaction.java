package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "SocialReaction")
public class SocialReaction {
    private String id;
    private String userId;
    private Reaction reaction;

    public SocialReaction(String userId, Reaction reaction) {
        this.userId = userId;
        this.reaction = reaction;
    }
}
