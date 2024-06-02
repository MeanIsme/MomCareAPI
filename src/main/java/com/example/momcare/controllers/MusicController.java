package com.example.momcare.controllers;

import com.example.momcare.payload.response.Response;
import com.example.momcare.service.MusicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicController {

    MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("music/random")
    public Response getRandomMusic(){
        return musicService.getRandomMusic();
    }
    @GetMapping("music/category")
    public Response getMusicByCategory(@RequestParam String category){
        return musicService.getMusicByCategory(category);
    }
    @GetMapping("music/allcategory")
    public Response getCategory(){
        return musicService.getCategory();
    }
}
