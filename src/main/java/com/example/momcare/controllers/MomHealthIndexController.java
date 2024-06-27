package com.example.momcare.controllers;

import com.example.momcare.exception.ResourceNotFoundException;
import com.example.momcare.payload.request.MomHealthIndexRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.MomHealthIndexService;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
public class MomHealthIndexController {


    private final MomHealthIndexService momHealthIndexService;

    public MomHealthIndexController(MomHealthIndexService momHealthIndexService) {
        this.momHealthIndexService = momHealthIndexService;
    }

    @PostMapping("/momindex/new")
    public Response createIndex(@RequestBody MomHealthIndexRequest momIndex) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), momHealthIndexService.createMomHealthIndex(momIndex), Constant.SUCCESS);
        } catch (ResourceNotFoundException e) {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), e.getMessage());
        }

    }

    @PutMapping("/momindex/update")
    public Response updateIndex(@RequestBody MomHealthIndexRequest momIndex) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), momHealthIndexService.updateMomHealthIndex(momIndex), Constant.SUCCESS);
        } catch (ResourceNotFoundException e) {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("/momindex/delete")
    public Response deleteIndex(@RequestBody MomHealthIndexRequest momIndex) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), momHealthIndexService.deleteMomHealthIndex(momIndex), Constant.SUCCESS);
        } catch (ResourceNotFoundException e) {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), e.getMessage());
        }
    }

    @GetMapping("/momindex/getall")
    public Response getIndex(@RequestParam String userID) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(), momHealthIndexService.getMomHealthIndex(userID), Constant.SUCCESS);
        } catch (ResourceNotFoundException e) {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), e.getMessage());
        }
    }

    @GetMapping("/momindex/standardsindex")
    public Response getIndex() {
        return new Response(HttpStatus.OK.getReasonPhrase(), momHealthIndexService.getIndexStandard(), Constant.SUCCESS);
    }

}
