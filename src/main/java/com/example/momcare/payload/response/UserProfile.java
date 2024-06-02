package com.example.momcare.payload.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserProfile {
    private String id;
    private String username;
    private String displayName;
    private String avtUrl;
    private Set<String> followers;
    private Set<String> share;
}
