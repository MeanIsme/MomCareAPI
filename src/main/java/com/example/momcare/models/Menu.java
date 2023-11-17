package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "Menu")
public class Menu {
    @Id
    private String id;
    private String title;
    private String content;
    private String category;
    private String thumbnail;
}
