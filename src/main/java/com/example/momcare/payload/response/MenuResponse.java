package com.example.momcare.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuResponse {
    private String id;
    private String title;
    private String thumbnail;
    private List<Integer> rate;

    public MenuResponse(String id, String title, String thumbnail, List<Integer> rate) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.rate = rate;
    }


}
