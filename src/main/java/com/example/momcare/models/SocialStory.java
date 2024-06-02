package com.example.momcare.models;


import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;


@Getter
@Setter
public class SocialStory{
    private Media media;
    private String time;

    public SocialStory() {
    }

    public SocialStory( Media media, String time) {
        this.media = media;
        this.time = time;
    }

    public SocialStory(Media media) {
        this.media = media;
        this.time = LocalDate.now().toString();
    }

}
