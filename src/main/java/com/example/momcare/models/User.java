package com.example.momcare.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString

@Document(collection = "Account")
public class User   {
    @Id
    private String id;
    @NotBlank
    @Size(max = 20)
    private String userName;
    @NotBlank
    @Size(max = 120)
    private String passWord;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private String datePregnant;
    private Boolean premium;
    private List<MomHealthIndex> momIndex;
    private List<BabyHealthIndex> babyIndex;
    private Boolean enabled;
    private String token;

}