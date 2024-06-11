package com.example.momcare.controllers;

import com.example.momcare.payload.request.NotificationRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.NotificationService;
import com.example.momcare.util.Constant;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @PostMapping
    public Response save(@RequestBody NotificationRequest notificationRequest) {
        try {
            return new Response(Constant.SUCCESS, notificationService.save(notificationRequest), "Notification saved");
        } catch (Exception e) {
            return new Response(Constant.FAILURE, null, e.getMessage());
        }
    }

    @PutMapping("/markAsRead")
    public Response markAsRead(@RequestParam String id) {
        try {
            return notificationService.markAsRead(id);
        } catch (Exception e) {
            return new Response(Constant.FAILURE, null, e.getMessage());
        }
    }

    @GetMapping("/byReceiver")
    public Response getAllByReceiverId(@RequestParam String receiverId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return notificationService.getAllByReceiverId(receiverId, page, size);
    }

    @GetMapping("/unread")
    public Response getUnreadNotifications(@RequestParam String receiverId,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return notificationService.getUnreadNotifications(receiverId, page, size);
    }

    @DeleteMapping("/delete")
    public Response deleteNotification(@RequestParam String id) {
        return notificationService.deleteNotification(id);
    }
}

