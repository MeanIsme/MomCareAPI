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

    UserStoryService userStoryService;

    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @PostMapping("/newOrAddStory")
    public Response newUserStory(@RequestBody UserStoryNewRequest request) {
        return userStoryService.newUserStory(request);
    }

    @PutMapping("/deleteStory")
    public Response deleteStory(@RequestBody UserStoryDeleteRequest request) {
        return userStoryService.deleteStory(request);
    }

    @GetMapping("/getAllById")
    public Response getAllById(@RequestParam String userId) {
        return userStoryService.getAllById(userId);
    }

    @GetMapping("/getAll")
    public Response getAll() {
        return userStoryService.getAll();
    }
}
