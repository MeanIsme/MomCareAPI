package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public abstract class Media {
    private String url;
    private String ration;
    private String description;
    private String uploadDate;
    private List<String> commentID;
    private List<String> reactID;
    private List<String> shareID;
    private int reactCount;
    private int commentCount;
    private int shareCount;

    public Media(String url, String ration, String description, int reactCount, int commentCount, int shareCount) {
        this.url = url;
        this.ration = ration;
        this.description = description;
        this.reactCount = reactCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
    }
}
