package com.example.momcare.service;

import com.example.momcare.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }


}
