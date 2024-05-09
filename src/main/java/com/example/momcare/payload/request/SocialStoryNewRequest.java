package com.example.momcare.payload.request;

import com.example.momcare.models.Media;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class SocialStoryNewRequest {
    private String description;
    private String userId;
    private List<String> media;
}
