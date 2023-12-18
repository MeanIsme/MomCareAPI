package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@Document(collection = "PeriodicCheck")
public class PeriodicCheck {
    @Id
    private String id;
    private int weekFrom;
    private int weekEnd;
    private List<String> thumbnails;
    private String title;
    private String obligatory;
    private String advice;
    private String note;
}
