package com.example.momcare.payload.response;

import com.example.momcare.models.BabyHealthIndex;
import com.example.momcare.models.MomHealthIndex;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private String id;
    private String userName;
    private String email;
    private String datePregnant;
    private Boolean premium;
    private List<MomHealthIndex> momIndex;
    private List<BabyHealthIndex> babyIndex;
    public UserResponse(String id, String userName, String email, String datePregnant, Boolean premium) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.datePregnant = datePregnant;
        this.premium = premium;
    }

    public UserResponse(String id, String userName, String email, String datePregnant, Boolean premium, List<MomHealthIndex> momIndex, List<BabyHealthIndex> babyIndex) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.datePregnant = datePregnant;
        this.premium = premium;
        this.momIndex = momIndex;
        this.babyIndex = babyIndex;
    }
}
