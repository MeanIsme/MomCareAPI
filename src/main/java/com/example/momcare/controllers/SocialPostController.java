package com.example.momcare.controllers;

import com.example.momcare.payload.request.ShareResquest;
import com.example.momcare.payload.request.SocialPostNewRequest;
import com.example.momcare.payload.request.SocialPostUpdateResquest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.SocialPostService;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/socialpost")
public class SocialPostController {
    SocialPostService socialPostService;

    public SocialPostController(SocialPostService socialPostService) {
        this.socialPostService = socialPostService;
    }

    @GetMapping("/getallbyuser")
    public Response getAllByUser(@RequestParam String userId) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), socialPostService.getAllByUserService(userId), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }
    @GetMapping("/getById")
    public Response getById(@RequestParam String id) {
        return new Response(HttpStatus.OK.getReasonPhrase(), socialPostService.getById(id), Constant.SUCCESS);
    }

    @GetMapping("/pagebyuser")
    public Response getAllByUser(@RequestParam String userId, int time) {
        return new Response(HttpStatus.OK.getReasonPhrase(), socialPostService.getAllByUser(userId, time), Constant.SUCCESS);
    }

    @GetMapping("/getall")
    public Response getAll() {
        return new Response(HttpStatus.OK.getReasonPhrase(), socialPostService.getAllService(), Constant.SUCCESS);
    }

    @GetMapping("/like")
    public Response search(@RequestParam String keyWord) {
        return new Response(HttpStatus.OK.getReasonPhrase(), socialPostService.getAllService(), Constant.SUCCESS);
    }

    @GetMapping("/page")
    public Response postPerPage(@RequestParam int time) {
        return new Response(HttpStatus.OK.getReasonPhrase(), socialPostService.postPerPageService(time), Constant.SUCCESS);
    }
    @GetMapping("/getAllReactionsByPostId")
    public Response postPerPage(@RequestParam String id) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), socialPostService.postPerPage(id), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }
    @PostMapping("/new")
    public Response create(@RequestBody SocialPostNewRequest request) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), socialPostService.create(request), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("/update")
    public Response update(@RequestBody SocialPostUpdateResquest request) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), socialPostService.update(request), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("/share")
    public Response share(@RequestBody ShareResquest request) {
        try {
            socialPostService.share(request);
            return new Response(HttpStatus.OK.getReasonPhrase(), new ArrayList<>(), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("/unshare")
    public Response unshare(@RequestBody ShareResquest request) {
        try {
            socialPostService.unshare(request);
            return new Response(HttpStatus.OK.getReasonPhrase(), new ArrayList<>(), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public Response delete(@RequestParam String idPost) {
        try {
            socialPostService.deletePost(idPost);
            return new Response(HttpStatus.OK.getReasonPhrase(), new ArrayList<>(), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }
}
