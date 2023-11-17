package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "MenuCategory")
public class MenuCategory {
    @Id
    private String id;
    private String name;
    private String thumbnail;

}
