package com.example.momcare.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "Category")
public class HandBookCategory {
    @Id
    private String id;
    @NotBlank
    private String name;
    @DBRef
    private List<String> collection;
    private String content;
    private String thumbnail;
}
