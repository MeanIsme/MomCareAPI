package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
public class SocialReaction {
    private String userName;
    private String urlAvt;
    private String time;
    private Reaction reaction;

    public SocialReaction() {
    }

    public SocialReaction( Reaction reaction) {
        this.reaction = reaction;
    }

    public SocialReaction(String userName, String urlAvt, String time, Reaction reaction) {
        this.userName = userName;
        this.urlAvt = urlAvt;
        this.time = time;
        this.reaction = reaction;
    }
}
