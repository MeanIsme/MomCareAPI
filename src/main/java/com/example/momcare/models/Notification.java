package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Notification {
    private String id;
    private String senderId;
    private String receiverId;
    private String timestamp;
    private boolean isRead;
    private NotificationType notificationType;
    public void markAsRead(){
        this.setRead(true);
    }
}
