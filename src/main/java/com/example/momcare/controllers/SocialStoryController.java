package com.example.momcare.controllers;

import com.example.momcare.models.SocialPost;
import com.example.momcare.models.SocialStory;
import com.example.momcare.payload.request.SocialPostNewRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.SocialStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/story")
public class SocialStoryController {
    @Autowired
    SocialStoryService socialStoryService;
    @PostMapping("/new")
    public Response create(@RequestBody SocialPostNewRequest request) {
        SocialStory socialStory = new SocialStory(request.getDescription(), request.getUserId(), request.getMedia(), LocalDateTime.now().toString(), LocalDateTime.now().plusDays(1).toString());
        if (socialStoryService.save(socialStory)) {
            List<SocialPost> socialPosts = new ArrayList<>();
            socialPosts.add(socialStory);
            return new Response((HttpStatus.OK.getReasonPhrase()), socialPosts, "success");
        } else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
    }
}
