package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "TrackingPregnancy")
public class Tracking {
    @Id
    private String id;

    private int week;
    private List<String> thumbnails;
    private String baby;
    private String mom;
    private String advice;

}
