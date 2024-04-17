package com.example.momcare.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserFollowerRequest {
    private String idUser;
    private String idFollowerUser;
}
