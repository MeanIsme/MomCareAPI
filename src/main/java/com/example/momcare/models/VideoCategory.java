package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "VideoCategory")
public class VideoCategory {
    @Id
    private String id;
    private String name;
    private String thumbnail;
    private String content;
}
