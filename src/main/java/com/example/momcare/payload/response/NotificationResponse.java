package com.example.momcare.payload.response;

import com.example.momcare.models.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private String id;
    private String senderId;
    private String receiverId;
    private String timestamp;
    private boolean isRead;
    private NotificationType notificationType;
    private String senderName;
    private String senderAvt;
    private String targetId;
}
