package com.example.momcare.controllers;

import com.example.momcare.payload.request.NotificationRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.NotificationService;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
            return new Response(HttpStatus.OK.getReasonPhrase(), notificationService.save(notificationRequest), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("/markAsRead")
    public Response markAsRead(@RequestParam String id) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), notificationService.markAsRead(id), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @GetMapping("/byReceiver")
    public Response getAllByReceiverId(@RequestParam String receiverId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), notificationService.getAllByReceiverId(receiverId, page, size), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @GetMapping("/unread")
    public Response getUnreadNotifications(@RequestParam String receiverId,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), notificationService.getUnreadNotifications(receiverId, page, size), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public Response deleteNotification(@RequestParam String id) {
        try {
            notificationService.deleteNotification(id);
            return new Response(HttpStatus.OK.getReasonPhrase(), new ArrayList<>(), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }
}

