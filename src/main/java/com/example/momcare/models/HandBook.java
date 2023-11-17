package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@ToString
@Document(collection = "HandBook")
public class HandBook {
    @Id
    private String id;
    private List<String> category;
    private String title;
    private String content;
    private String thumbnail;
    private Date time;
}
