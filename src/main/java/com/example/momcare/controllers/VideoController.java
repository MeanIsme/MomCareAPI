package com.example.momcare.controllers;

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
    @Autowired
    private VideoService videoService;
    @GetMapping("video/all")
    public Response GetAllVideo(){
        return new Response((HttpStatus.OK.getReasonPhrase()), videoService.findall(), "success");
    }
    @GetMapping("video/category")
    public Response GetVideoByCategory(@RequestParam String category){
        return new Response((HttpStatus.OK.getReasonPhrase()), videoService.findByCategory(category), "success");
    }
}
