package com.example.momcare.payload.request;

import com.example.momcare.models.Media;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
public class SocialPostNewRequest
{
    private String description;
    private String userId;
    private String userName;
    private String displayName;
    private String avtUrl;
    private List<Media> media;

}
