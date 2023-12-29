package com.example.momcare.controllers;

import com.example.momcare.models.Category;
import com.example.momcare.payload.response.Response;
import com.example.momcare.repository.MusicRepository;
import com.example.momcare.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicController {
    @Autowired
    MusicService musicService;

    @GetMapping("music/random")
    public Response GetRandomMusic(){
        return new Response((HttpStatus.OK.getReasonPhrase()), musicService.Top8Random(), "success");
    }
    @GetMapping("music/category")
    public Response GetMusicByCategory(@RequestBody Category category){
        return new Response((HttpStatus.OK.getReasonPhrase()), musicService.findMusicByCategory(category), "success");
    }
    @GetMapping("music/allcategory")
    public Response GetCategory(){
        return new Response((HttpStatus.OK.getReasonPhrase()), musicService.getAllCategories(), "success");
    }
}
