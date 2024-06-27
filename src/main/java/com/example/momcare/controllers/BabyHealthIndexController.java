package com.example.momcare.controllers;

import com.example.momcare.exception.ResourceNotFoundException;
import com.example.momcare.models.BabyHealthIndex;
import com.example.momcare.payload.request.BabyHealthIndexRequest;
import com.example.momcare.payload.request.StandIndexRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.BabyHealthIndexService;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/babyindex")
public class BabyHealthIndexController {
    private final BabyHealthIndexService babyHealthIndexService;

    public BabyHealthIndexController(BabyHealthIndexService babyHealthIndexService) {
        this.babyHealthIndexService = babyHealthIndexService;
    }

    @PostMapping("/new")
    public Response createIndex(@RequestBody BabyHealthIndexRequest babyIndex) {
        try
        {
            List<BabyHealthIndex>  babyHealthIndices = babyHealthIndexService.createBabyHealthIndex(babyIndex);
            return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthIndices, Constant.SUCCESS);
        }catch (Exception e){
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("/update")
    public Response updateIndex(@RequestBody BabyHealthIndexRequest babyIndex) {
        try{
            List<BabyHealthIndex> babyHealthIndices = babyHealthIndexService.updateBabyHealthIndex(babyIndex);
            return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthIndices, Constant.SUCCESS);
        }catch (Exception e)
        {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), e.getMessage());
        }

    }

    @PutMapping("/delete")
    public Response deleteIndex(@RequestBody BabyHealthIndexRequest babyIndex) {
        try{
            List<BabyHealthIndex> babyHealthIndices = babyHealthIndexService.deleteBabyHealthIndex(babyIndex);
            return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthIndices, Constant.SUCCESS);
        }catch (Exception e){
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), e.getMessage());
        }
    }

    @GetMapping("/getall")
    public Response getIndex(@RequestParam String userID) {
        try {
            List<BabyHealthIndex> babyHealthIndices = babyHealthIndexService.getAllBabyHealthIndices(userID);
            return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthIndices, Constant.SUCCESS);
        } catch (ResourceNotFoundException e) {
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("/standardsindex")
    public Response getStandardIndex(@RequestBody StandIndexRequest request) {
        return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthIndexService.getStandardBabyIndex(request.getDatePregnant(), request.getDateEnd()), Constant.SUCCESS);
    }
}