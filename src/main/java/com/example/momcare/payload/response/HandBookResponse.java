package com.example.momcare.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
public class HandBookResponse {

    private String id;
    private List<String> category;
    private String title;
    private String thumbnail;
    private Date time;

    public HandBookResponse(String id, List<String> category, String title, String thumbnail, Date time) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.thumbnail = thumbnail;
        this.time = time;
    }
}
