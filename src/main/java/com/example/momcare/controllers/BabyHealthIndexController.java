package com.example.momcare.controllers;

import com.example.momcare.models.BabyHealthIndex;
import com.example.momcare.models.Diary;
import com.example.momcare.models.User;
import com.example.momcare.payload.request.BabyHealthIndexRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BabyHealthIndexController {
    @Autowired
    private UserService service;
    @PostMapping("/babyindex/new")
    public Response CreateIndex(@RequestBody BabyHealthIndexRequest babyIndex){
        User user = service.findAccountByID(babyIndex.getUserID());
        if(user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<BabyHealthIndex> babyHealthIndices = new ArrayList<>();
        BabyHealthIndex babyHealthIndex = new BabyHealthIndex(
                babyIndex.getHead(),
                babyIndex.getBiparietal(),
                babyIndex.getOccipitofrontal(),
                babyIndex.getAbdominal(),
                babyIndex.getFemur(),
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString());
        if(user.getBabyIndex() != null){
            babyHealthIndices.addAll(user.getBabyIndex());
        }
        babyHealthIndices.add(babyHealthIndex);
        user.setBabyIndex(babyHealthIndices);
        service.update(user);
        return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthIndices , "success");
    }
    @PutMapping("/babyindex/update")
    public Response UpdateIndex(@RequestBody BabyHealthIndexRequest babyIndex){
        User user = service.findAccountByID(babyIndex.getUserID());
        if(user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<BabyHealthIndex> babyHealthIndices = new ArrayList<>();
        if(user.getBabyIndex() != null){
            babyHealthIndices.addAll(user.getBabyIndex());
            babyHealthIndices.get(babyIndex.getIndex()).setHead(babyIndex.getHead());
            babyHealthIndices.get(babyIndex.getIndex()).setBiparietal(babyIndex.getBiparietal());
            babyHealthIndices.get(babyIndex.getIndex()).setOccipitofrontal(babyIndex.getOccipitofrontal());
            babyHealthIndices.get(babyIndex.getIndex()).setAbdominal(babyIndex.getAbdominal());
            babyHealthIndices.get(babyIndex.getIndex()).setFemur(babyIndex.getFemur());
            babyHealthIndices.get(babyIndex.getIndex()).setTimeUpdate(LocalDateTime.now().toString());
            service.update(user);
            return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthIndices , "success");
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Index not found");

    }
    @DeleteMapping("/babyindex/delete")
    public Response DeleteIndex(@RequestBody BabyHealthIndexRequest babyIndex){
        User user = service.findAccountByID(babyIndex.getUserID());
        if(user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<BabyHealthIndex> babyHealthIndices = new ArrayList<>();
        if(user.getBabyIndex() != null){
            babyHealthIndices.addAll(user.getBabyIndex());
            int index = babyIndex.getIndex();
            babyHealthIndices.remove(index);
            user.setBabyIndex(babyHealthIndices);
            service.update(user);
            return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthIndices , "success");
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Index not found");

    }
    @GetMapping("/babyindex/getall")
    public Response GetIndex(@RequestParam String userID){
        User user = service.findAccountByID(userID);
        if(user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<BabyHealthIndex> babyHealthIndices = new ArrayList<>();
        if(user.getBabyIndex() != null){
            babyHealthIndices.addAll(user.getBabyIndex());
            return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthIndices , "success");
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Index not found");

    }


}
