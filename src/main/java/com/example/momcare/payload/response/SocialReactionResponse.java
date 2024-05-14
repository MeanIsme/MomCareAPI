package com.example.momcare.payload.response;

import com.example.momcare.models.Reaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
@Getter
@Setter
@AllArgsConstructor
public class SocialReactionResponse {
    private String avtUrl;
    private String displayName;
    private String time;
    private Reaction reaction;
}
