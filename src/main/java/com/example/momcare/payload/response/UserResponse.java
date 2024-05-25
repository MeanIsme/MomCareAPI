package com.example.momcare.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserResponse {
    private String id;
    private String userName;
    private String email;
    private String datePregnant;
    private Boolean premium;
    private String avtUrl;
    private Set<String> follower;
    private Set<String> following;
    private String nameDisplay;

    public UserResponse(String id, String userName, String email, String datePregnant, Boolean premium, String avtUrl) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.datePregnant = datePregnant;
        this.premium = premium;
        this.avtUrl = avtUrl;
    }

    public UserResponse(String id, String userName, String email, String datePregnant, Boolean premium) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.datePregnant = datePregnant;
        this.premium = premium;
    }

    public UserResponse(String id, String userName, String email, String datePregnant, Boolean premium, String avtUrl, Set<String> follower, Set<String> following) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.datePregnant = datePregnant;
        this.premium = premium;
        this.avtUrl = avtUrl;
        this.follower = follower;
        this.following = following;
    }

    public UserResponse(String id, String userName, String email, String datePregnant, Boolean premium, String avtUrl, Set<String> follower, Set<String> following, String nameDisplay) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.datePregnant = datePregnant;
        this.premium = premium;
        this.avtUrl = avtUrl;
        this.follower = follower;
        this.following = following;
        this.nameDisplay = nameDisplay;
    }

}
