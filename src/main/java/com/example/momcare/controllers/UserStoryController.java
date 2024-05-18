package com.example.momcare.controllers;

import com.example.momcare.models.SocialStory;
import com.example.momcare.models.User;
import com.example.momcare.models.UserStory;
import com.example.momcare.payload.request.UserStoryDeleteRequest;
import com.example.momcare.payload.request.UserStoryNewRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.payload.response.UserStoryResponse;
import com.example.momcare.service.UserService;
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
    @Autowired
    UserService userService;
    @PostMapping("/newOrAddStory")
    public Response newUserStory(@RequestBody UserStoryNewRequest request) {
        if(request.getUserId()== null){
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Not found User");
        }
        UserStory userStoryRequest = userStoryService.findByUserId(request.getUserId());

        if (userStoryRequest == null) {
            UserStory userStory = new UserStory(request.getUserId(), request.getSocialStory());
            return getResponse(userStory);
        }
        else {
            UserStory userStory = new UserStory(userStoryRequest.getId(), request.getUserId(),
                    userStoryRequest.getSocialStories(), request.getSocialStory());
            return getResponse(userStory);
        }


    }

    private Response getResponse(UserStory userStory) {
        if (userStoryService.save(userStory)) {
            List<UserStoryResponse> userStoryResponses = new ArrayList<>();
            User user = userService.findAccountByID(userStory.getUserId());
            userStoryResponses.add(new UserStoryResponse(userStory.getId(), user.getUserName(), user.getNameDisplay(),
                    userStory.getUserId(), user.getAvtUrl(), userStory.getSocialStories()));
            return new Response((HttpStatus.OK.getReasonPhrase()), userStoryResponses, "success");
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
            UserStory userStory = new UserStory(userStoryRequest.getId(), userStoryRequest.getUserId(),request.getSocialStories());
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
            List<UserStoryResponse> userStoryResponses = new ArrayList<>();
            User user = userService.findAccountByID(userStoryRequest.getUserId());
            userStoryResponses.add(new UserStoryResponse(userStoryRequest.getId(), user.getUserName(), user.getNameDisplay(),
                    userStoryRequest.getUserId(), user.getAvtUrl(), userStoryRequest.getSocialStories()));
            return new Response((HttpStatus.OK.getReasonPhrase()), userStoryResponses, "success");
        }

    }
    @GetMapping("/getAll")
    public Response getAll() {
        List<UserStory> userStories = userStoryService.findall();
        List<UserStoryResponse> userStoryResponses = new ArrayList<>();
        if (userStories == null) {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Not found");
        }
        for (UserStory userStory : userStories) {
            User user = userService.findAccountByID(userStory.getUserId());
            userStoryResponses.add(new UserStoryResponse(userStory.getId(), user.getUserName(), user.getNameDisplay(),
                    userStory.getUserId(), user.getAvtUrl(), userStory.getSocialStories()));
        }
        return new Response((HttpStatus.OK.getReasonPhrase()), userStoryResponses, "success");
    }
}
