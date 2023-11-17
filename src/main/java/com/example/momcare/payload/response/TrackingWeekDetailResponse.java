package com.example.momcare.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class TrackingWeekDetailResponse {
    private String id;
    private int week;
    private String name;
    private String baby;
    private String mom;
    private String advice;
    private List<String> thumbnail;

    public TrackingWeekDetailResponse(String id, int week, String name, String baby, String mom, String advice, List<String> thumbnail) {
        this.id = id;
        this.week = week;
        this.name = name;
        this.baby = baby;
        this.mom = mom;
        this.advice = advice;
        this.thumbnail = thumbnail;
    }


}
