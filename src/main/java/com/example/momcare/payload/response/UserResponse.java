package com.example.momcare.payload.response;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserResponse {
    private String id;
    private String userName;
    private String email;
    private String datePregnant;
    private Boolean premium;

    public UserResponse(String id, String userName, String email, String datePregnant, Boolean premium) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.datePregnant = datePregnant;
        this.premium = premium;
    }
}
