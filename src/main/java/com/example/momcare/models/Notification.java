package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "Notification")
public class Notification {
    private String id;
    private String senderId;
    private String receiverId;
    private String targetId;
    private String timestamp;
    private boolean isRead;
    private NotificationType notificationType;
    public void markAsRead(){
        this.setRead(true);
    }
}
