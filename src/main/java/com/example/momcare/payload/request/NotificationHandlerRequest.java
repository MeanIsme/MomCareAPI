package com.example.momcare.payload.request;

import com.example.momcare.models.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationHandlerRequest {
    private String senderId;
    private String receiverId;
    private String targetId;
    private NotificationType notificationType;
}
