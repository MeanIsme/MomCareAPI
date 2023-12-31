package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@Document(collection = "Video")
public class Video {
    @Id
    private String id;
    private String title;
    private String link;
    private String content;
    private String thumbnail;
    private List<String> category;
}
