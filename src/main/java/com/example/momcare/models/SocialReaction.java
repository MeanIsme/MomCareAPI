package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class SocialReaction {

    private String time = LocalDateTime.now().toString();
    private Reaction reaction;

    public SocialReaction() {
    }

    public SocialReaction( Reaction reaction) {
        this.reaction = reaction;
    }

    public SocialReaction(Reaction reaction, String time) {
        this.time = time;
        this.reaction = reaction;
    }
}
