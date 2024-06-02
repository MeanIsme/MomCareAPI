package com.example.momcare.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Media {
    private String url;
    private String ratio;
    private String description;
    private String uploadDate;
    private MediaType type;


}
