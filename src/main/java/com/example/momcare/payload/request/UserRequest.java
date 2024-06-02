package com.example.momcare.payload.request;

import com.example.momcare.models.BabyHealthIndex;
import com.example.momcare.models.MomHealthIndex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String id;
    private String userName;
    private String passWord;
    private String email;
    private String avtUrl;
    private String nameDisplay;
    private Set<String> follower;
    private Set<String> following;
    private Set<String> shared;
    private String datePregnant;
    private Boolean premium;
    private List<MomHealthIndex> momIndex;
    private List<BabyHealthIndex> babyIndex;

}
