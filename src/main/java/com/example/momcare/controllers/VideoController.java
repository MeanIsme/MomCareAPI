package com.example.momcare.controllers;

import com.example.momcare.models.Category;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("video/random")
    public Response getRandomVideo(){
        return videoService.getRandomVideo();
    }
    @GetMapping("video/category")
    public Response getVideoByCategory(@RequestParam String category){
        return videoService.getVideoByCategory(category);
    }
    @GetMapping("video/allcategory")
    public Response getCategory(){
        return videoService.getCategory();
    }
}
