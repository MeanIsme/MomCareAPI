package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@ToString
@Document(collection = "Diary")
public class Diary {
    @Id
    private String id;
    private String idUser;
    private String title;
    private String content;
    private List<String> thumbnail;
    private int reaction;
    private String timeCreate;
    private String timeUpdate;

    public Diary() {
    }

    public Diary(String idUser, String title, String content, List<String> thumbnail,int reaction, String timeCreate, String timeUpdate) {
        this.idUser = idUser;
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.reaction = reaction;
        this.timeCreate = timeCreate;
        this.timeUpdate = timeUpdate;
    }



    public Diary(String id, String idUser, String title, String content, List<String> thumbnail, int reaction, String timeCreate, String timeUpdate) {
        this.id = id;
        this.idUser = idUser;
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.reaction = reaction;
        this.timeCreate = timeCreate;
        this.timeUpdate = timeUpdate;
    }
}
