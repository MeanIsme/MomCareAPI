package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "SocialComment")
public class SocialComment {
    private String id;
    private String userId;

    private String description;
    private String time;

    public SocialComment(String userId, String description, String time) {
        this.userId = userId;
        this.description = description;
        this.time = time;
    }
}

