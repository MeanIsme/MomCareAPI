package com.example.momcare.payload.request;

import com.example.momcare.models.Media;
import com.example.momcare.models.SocialReaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;
@Getter
@Setter
public class SocialPostUpdateResquest {
    private String id;
    private String description;
    private List<Media> media;
    private Map<String, SocialReaction> reaction;
}
