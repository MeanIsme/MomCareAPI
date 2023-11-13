package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "TrackingPregnancy")
public class Tracking {
    @Id
    private String id;

    private int week;
    private TrackingBaby baby;
    private TrackingMom mom;
    private TrackingAdvice advice;

}
