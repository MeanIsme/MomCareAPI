package com.example.momcare.service;

import com.example.momcare.models.Notification;
import com.example.momcare.payload.request.NotificationRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.repository.NotificationRepository;
import com.example.momcare.util.Constant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    @Transactional
    public List<Notification> save(NotificationRequest notificationRequest) {
        Notification notification = map(notificationRequest);
        List<Notification> notifications = new ArrayList<>();
        notifications.add(this.notificationRepository.save(notification));
        return notifications;
    }

    private Notification map(NotificationRequest notificationRequest) {
        Notification notification = new Notification();
        notification.setReceiverId(notificationRequest.getReceiverId());
        notification.setSenderId(notificationRequest.getSenderId());
        notification.setTimestamp(notificationRequest.getTimestamp());
        notification.setRead(false);
        notification.setNotificationType(notificationRequest.getNotificationType());
        return notification;
    }

    @Transactional
    public Response markAsRead(String id) {
        Notification notification = this.notificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.markAsRead();
        this.notificationRepository.save(notification);
        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification);
        return new Response( Constant.SUCCESS, notifications, "Notification marked as read");
    }

    public Response getAllByReceiverId(String receiverId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notificationsPage = notificationRepository.findByReceiverId(receiverId, pageable);
        List<Notification> notifications = notificationsPage.getContent();
        return new Response( Constant.SUCCESS, notifications, "Get all notifications by receiverId");
    }
    public Response getUnreadNotifications(String receiverId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notificationsPage = notificationRepository.findByReceiverIdAndIsRead(receiverId, false, pageable);
        List<Notification> notifications = notificationsPage.getContent();
        return new Response( Constant.SUCCESS, notifications, "Get all unread notifications by receiverId");
    }
    @Transactional
    public Response deleteNotification(String id) {
        Notification notification = this.notificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        this.notificationRepository.delete(notification);
        return new Response( Constant.SUCCESS, null, "Notification deleted");
    }


}
