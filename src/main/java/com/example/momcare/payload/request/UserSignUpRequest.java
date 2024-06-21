package com.example.momcare.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSignUpRequest {
    private String userName;
    private String email;
    private String nameDisplay;
    private String password;
}
