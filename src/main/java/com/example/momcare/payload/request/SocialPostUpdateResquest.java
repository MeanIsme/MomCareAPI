package com.example.momcare.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class SocialPostUpdateResquest {
    private String id;
    private String description;
    Set<String> images;
}
