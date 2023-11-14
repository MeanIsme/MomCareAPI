package com.example.momcare.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingWeekResponse {
    private String id;
    private int week;
    private String name;
    private String type;

    public TrackingWeekResponse(String id, int week, String name, String type) {
        this.id = id;
        this.week = week;
        this.name = name;
        this.type = type;
    }

    public TrackingWeekResponse() {
    }
}
