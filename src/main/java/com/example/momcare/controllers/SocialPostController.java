package com.example.momcare.controllers;

import com.example.momcare.handler.NotificationHandler;
import com.example.momcare.models.SocialPost;
import com.example.momcare.models.SocialReaction;
import com.example.momcare.models.User;
import com.example.momcare.payload.request.ShareResquest;
import com.example.momcare.payload.request.SocialPostNewRequest;
import com.example.momcare.payload.request.SocialPostUpdateResquest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.payload.response.SocialPostResponse;
import com.example.momcare.payload.response.SocialReactionResponse;
import com.example.momcare.service.SocialCommentService;
import com.example.momcare.service.SocialPostService;
import com.example.momcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/socialpost")
public class SocialPostController {
    SocialPostService socialPostService;

    public SocialPostController(SocialPostService socialPostService) {
        this.socialPostService = socialPostService;
    }

    @GetMapping("/getallbyuser")
    public Response getAllByUser(@RequestParam String userId) {
        return socialPostService.getAllByUserService(userId);
    }
    @GetMapping("/getById")
    public Response getById(@RequestParam String id) {
        return socialPostService.getById(id);
    }

    @GetMapping("/pagebyuser")
    public Response getAllByUser(@RequestParam String userId, int time) {
        return socialPostService.getAllByUser(userId, time);
    }

    @GetMapping("/getall")
    public Response getAll() {
        return socialPostService.getAllService();
    }

    @GetMapping("/like")
    public Response search(@RequestParam String keyWord) {
        return socialPostService.search(keyWord);
    }

    @GetMapping("/page")
    public Response postPerPage(@RequestParam int time) {
        return socialPostService.postPerPageService(time);
    }
    @GetMapping("/getAllReactionsByPostId")
    public Response postPerPage(@RequestParam String id) {
        return socialPostService.postPerPage(id);
    }
    @PostMapping("/new")
    public Response create(@RequestBody SocialPostNewRequest request) {
        return socialPostService.create(request);
    }

    @PutMapping("/update")
    public Response update(@RequestBody SocialPostUpdateResquest request) {
        return socialPostService.update(request);
    }

    @PutMapping("/share")
    public Response share(@RequestBody ShareResquest request) {
        return socialPostService.share(request);
    }

    @PutMapping("/unshare")
    public Response unshare(@RequestBody ShareResquest request) {
        return socialPostService.unshare(request);
    }

    @DeleteMapping("/delete")
    public Response delete(@RequestParam String idPost) {
        return socialPostService.deletePost(idPost);
    }
}
