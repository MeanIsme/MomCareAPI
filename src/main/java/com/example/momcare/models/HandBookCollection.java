package com.example.momcare.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@ToString
@Document(collection = "HandBookCollection")
public class HandBookCollection {
    @Id
    private String id;
    @NotBlank
    private String name;
    private String thumbnail;

}
