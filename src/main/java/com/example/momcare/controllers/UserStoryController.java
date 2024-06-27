package com.example.momcare.controllers;

import com.example.momcare.payload.request.UserStoryDeleteRequest;
import com.example.momcare.payload.request.UserStoryNewRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.UserStoryService;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/userStory")
public class UserStoryController {

    UserStoryService userStoryService;

    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @PostMapping("/newOrAddStory")
    public Response newUserStory(@RequestBody UserStoryNewRequest request) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), userStoryService.newUserStory(request), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("/deleteStory")
    public Response deleteStory(@RequestBody UserStoryDeleteRequest request) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), userStoryService.deleteStory(request), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @GetMapping("/getAllById")
    public Response getAllById(@RequestParam String userId) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), userStoryService.getAllById(userId), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public Response getAll() {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), userStoryService.getAll(), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }
}
