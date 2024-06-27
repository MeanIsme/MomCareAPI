package com.example.momcare.controllers;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.VideoService;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("video/random")
    public Response getRandomVideo(){
        return new Response(HttpStatus.OK.getReasonPhrase(), videoService.top8Random(), Constant.SUCCESS);
    }
    @GetMapping("video/category")
    public Response getVideoByCategory(@RequestParam String category){
        return new Response(HttpStatus.OK.getReasonPhrase(), videoService.findByCategory(category), Constant.SUCCESS);
    }
    @GetMapping("video/allcategory")
    public Response getCategory(){
        return new Response(HttpStatus.OK.getReasonPhrase(), videoService.getAllCategories(), Constant.SUCCESS);
    }
}
