package com.example.momcare.controllers;

import com.example.momcare.payload.response.Response;
import com.example.momcare.service.MusicService;
import com.example.momcare.util.Constant;
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
        return new Response(HttpStatus.OK.getReasonPhrase(), musicService.getRandomMusic(), Constant.SUCCESS);
    }
    @GetMapping("music/category")
    public Response getMusicByCategory(@RequestParam String category){
        return new Response(HttpStatus.OK.getReasonPhrase(),  musicService.getMusicByCategory(category), Constant.SUCCESS);
    }
    @GetMapping("music/allcategory")
    public Response getCategory(){
        return new Response(HttpStatus.OK.getReasonPhrase(),  musicService.getCategory(), Constant.SUCCESS);
    }
}
