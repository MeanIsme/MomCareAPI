package com.example.momcare.models;


import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;


@Getter
@Setter
public class SocialStory{
    private String media;
    private String time;

    public SocialStory() {
    }

    public SocialStory( String media, String time) {
        this.media = media;
        this.time = time;
    }

    public SocialStory(String media) {
        this.media = media;
        this.time = LocalDate.now().toString();
    }

}
