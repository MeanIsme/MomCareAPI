package com.example.momcare.payload.request;

import com.example.momcare.models.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private String senderId;
    private String receiverId;
    private String timestamp;
    private NotificationType notificationType;
}
