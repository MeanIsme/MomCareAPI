package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Notification {
    private String id;
    private String senderName;
    private String message;
    private String imageUrl;
    private String timestamp;
    private boolean isRead;
    private NotificationType notificationType;
    public void markAsRead(){
        this.setRead(true);
    }
}
