package com.example.momcare.controllers;

import com.example.momcare.payload.request.SocialCommentNewRequest;
import com.example.momcare.payload.request.SocialCommentDeleteRequest;
import com.example.momcare.payload.request.SocialCommentUpdateRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.SocialCommentService;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/socialcomment")
public class SocialCommentController {

    SocialCommentService socialCommentService;

    public SocialCommentController(SocialCommentService socialCommentService) {
        this.socialCommentService = socialCommentService;
    }

    @GetMapping("/all")
    public Response getAll() {
        return new Response(HttpStatus.OK.getReasonPhrase(), socialCommentService.getAllSocialComments(), Constant.SUCCESS);
    }

    @GetMapping("/allByPostId")
    public Response getAllByPostId(@RequestParam String id) {
        return new Response(HttpStatus.OK.getReasonPhrase(), socialCommentService.getSocialCommentsById(id), Constant.SUCCESS);
    }

    @GetMapping("/getAllReactionsByCommentId")
    public Response postPerPage(@RequestParam String id) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), socialCommentService.getSocialCommentReactions(id), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @PostMapping("/new")
    public Response create(@RequestBody SocialCommentNewRequest request) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), socialCommentService.create(request), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("/update")
    public Response update(@RequestBody SocialCommentUpdateRequest request) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), socialCommentService.update(request), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("/addReaction")
    public Response addReaction(@RequestBody SocialCommentUpdateRequest request) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), socialCommentService.addReaction(request), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("/deleteReaction")
    public Response deleteReaction(@RequestBody SocialCommentUpdateRequest request) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), socialCommentService.deleteReaction(request), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("/delete")
    public Response delete(@RequestBody SocialCommentDeleteRequest socialCommentDeleteRequest) {
        try {
            socialCommentService.delete(socialCommentDeleteRequest);
            return new Response(HttpStatus.OK.getReasonPhrase(), new ArrayList<>(), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }
}
