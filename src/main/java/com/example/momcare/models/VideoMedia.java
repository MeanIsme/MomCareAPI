package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VideoMedia extends Media{
    private double duration;
    private String resolution;

    public VideoMedia(String url, String ration, String description, int reactCount, int commentCount, int shareCount, double duration, String resolution) {
        super(url, ration, description, reactCount, commentCount, shareCount);
        this.duration = duration;
        this.resolution = resolution;
    }
}
