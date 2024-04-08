package com.example.momcare.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class SocialPostNewRequest
{
        private String description;
    private String userId;
        Set<String> images;
}
