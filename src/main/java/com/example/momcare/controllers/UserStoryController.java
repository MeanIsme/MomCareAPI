package com.example.momcare.controllers;

import com.example.momcare.models.SocialStory;
import com.example.momcare.models.UserStory;
import com.example.momcare.payload.request.UserStoryDeleteRequest;
import com.example.momcare.payload.request.UserStoryNewRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.UserStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/userStory")
public class UserStoryController {
    @Autowired
    UserStoryService userStoryService;
    @PostMapping("/newOrAddStory")
    public Response newUserStory(@RequestBody UserStoryNewRequest request) {
        if(request.getUserName()== null){
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Not found User");
        }
        UserStory userStoryRequest = userStoryService.findByUserId(request.getUserId());
        if (userStoryRequest == null) {
            UserStory userStory = new UserStory(request.getUserName(), request.getDisplayName(), request.getUserId(),
                    request.getAvtUrl(), request.getSocialStory());
            return getResponse(userStory);
        }
        else {
            UserStory userStory = new UserStory(userStoryRequest.getId(),request.getUserName(), request.getDisplayName(), request.getUserId(),
                    request.getAvtUrl(),userStoryRequest.getSocialStories(), request.getSocialStory());
            return getResponse(userStory);
        }


    }

    private Response getResponse(UserStory userStory) {
        if (userStoryService.save(userStory)) {
            List<UserStory> userStories = new ArrayList<>();
            userStories.add(userStory);
            return new Response((HttpStatus.OK.getReasonPhrase()), userStories, "success");
        } else {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
        }
    }

    @PutMapping("/deleteStory")
    public Response deleteStory(@RequestBody UserStoryDeleteRequest request) {
        UserStory userStoryRequest = userStoryService.findByUserId(request.getUserId());
        if (userStoryRequest == null) {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Not found User");
        }
        else {
            UserStory userStory = new UserStory(userStoryRequest.getId(),userStoryRequest.getUserName(), userStoryRequest.getDisplayName(), userStoryRequest.getUserId(),
                    userStoryRequest.getAvtUrl(),request.getSocialStories());
            return getResponse(userStory);
        }
    }
    @GetMapping("/getAllById")
    public Response getAllById(@RequestParam String userId) {
        UserStory userStoryRequest = userStoryService.findByUserId(userId);
        if (userStoryRequest == null) {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Not found User");
        }
        else {
            List<UserStory> userStories = new ArrayList<>();
            userStories.add(userStoryRequest);
            return new Response((HttpStatus.OK.getReasonPhrase()), userStories, "success");
        }

    }
    @GetMapping("/getAll")
    public Response getAll() {
        return new Response((HttpStatus.OK.getReasonPhrase()), userStoryService.findall(), "success");
    }
}
