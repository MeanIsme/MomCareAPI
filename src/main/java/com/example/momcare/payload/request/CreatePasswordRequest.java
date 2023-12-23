package com.example.momcare.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePasswordRequest {
    private String userName;
    private String token;
    private String newPassword;
}
