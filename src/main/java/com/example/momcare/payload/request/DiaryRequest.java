package com.example.momcare.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiaryRequest {
    private String id;
    private String idUser;
    private String title;
    private String content;
    private List<String> thumbnail;
    private int reaction;
    private String timeCreate;
    private String timeUpdate;
}
