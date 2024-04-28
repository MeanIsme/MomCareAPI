package com.example.momcare.controllers;

import com.example.momcare.models.HandBook;
import com.example.momcare.models.SocialPost;
import com.example.momcare.payload.response.Response;
import com.example.momcare.payload.response.UserResponse;
import com.example.momcare.service.DiaryService;
import com.example.momcare.service.HandBookService;
import com.example.momcare.service.SocialPostService;
import com.example.momcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private UserService userService;
    @Autowired
    private SocialPostService socialPostService;
    @Autowired
    private HandBookService handBookService;


    @GetMapping("/handBook")
    public Response searchHandBook (@RequestParam String keyWord){
        List<HandBook> handBookList = new ArrayList<>(handBookService.searchHandBook(keyWord));
        return new Response(HttpStatus.OK.getReasonPhrase(), handBookList , "success");
    }
    @GetMapping("/socialPost")
    public Response searchSocialPost (@RequestParam String keyWord){
        List<SocialPost> socialPostList = new ArrayList<>(socialPostService.searchSocialPostByTitle(keyWord));
        return new Response(HttpStatus.OK.getReasonPhrase(), socialPostList , "success");
    }
    @GetMapping("/user")
    public Response searchUser (@RequestParam String keyWord){
        List<UserResponse> userResponses = new ArrayList<>(userService.searchUserByUserName(keyWord));
        return new Response(HttpStatus.OK.getReasonPhrase(), userResponses , "success");
    }
}
