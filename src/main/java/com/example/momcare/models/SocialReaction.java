package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
@Getter
@Setter
public class SocialReaction {

    private String time;
    private Reaction reaction;

    public SocialReaction() {
    }

    public SocialReaction( Reaction reaction) {
        this.time= LocalDateTime.now().toString();
        this.reaction = reaction;
    }

    public SocialReaction(Reaction reaction, String time) {
        this.time = time;
        this.reaction = reaction;
    }
}
