package com.example.momcare.payload.request;

import com.example.momcare.models.Media;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
public class SocialPostUpdateResquest {
    private String id;
    private String description;
    private List<Media> media;
}
