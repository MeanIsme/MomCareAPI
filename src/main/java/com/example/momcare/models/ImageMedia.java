package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImageMedia extends Media {
    private double width;
    private double height;
    private String format;

    public ImageMedia(String url, String ration, String description, int reactCount, int commentCount, int shareCount, double width, double height, String format) {
        super(url, ration, description, reactCount, commentCount, shareCount);
        this.width = width;
        this.height = height;
        this.format = format;
    }
}
