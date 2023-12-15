package com.example.momcare.controllers;

import com.example.momcare.models.BabyHealthIndex;
import com.example.momcare.models.Diary;
import com.example.momcare.models.User;
import com.example.momcare.models.WarningHealth;
import com.example.momcare.payload.request.BabyHealthIndexRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.BabyHealthIndexService;
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
    private UserService userService;
    @Autowired
    private BabyHealthIndexService babyHealthIndexService;
    @PostMapping("/babyindex/new")
    public Response CreateIndex(@RequestBody BabyHealthIndexRequest babyIndex){
        User user = userService.findAccountByID(babyIndex.getUserID());
        if(user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<BabyHealthIndex> babyHealthIndices = new ArrayList<>();
        List<BabyHealthIndex> babyHealthResponse = new ArrayList<>();
        BabyHealthIndex babyHealthIndex = new BabyHealthIndex(
                babyIndex.getHead(),
                babyIndex.getBiparietal(),
                babyIndex.getOccipitofrontal(),
                babyIndex.getAbdominal(),
                babyIndex.getFemur(),
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString());
        List<WarningHealth> warningHealths = new ArrayList<>();
        int ga = userService.gestationalAge(user.getDatePregnant(), babyHealthIndex.getTimeCreate());
        if(babyHealthIndex.getHead()!=null)
            warningHealths.add(babyHealthIndexService.CheckHead(ga,babyHealthIndex.getHead()));
        if (babyHealthIndex.getBiparietal()!=null)
            warningHealths.add(babyHealthIndexService.CheckBiparietal(ga, babyHealthIndex.getBiparietal()));
        if(babyHealthIndex.getOccipitofrontal()!=null)
            warningHealths.add(babyHealthIndexService.CheckOccipitofrontal(ga, babyHealthIndex.getOccipitofrontal()));
        if (babyHealthIndex.getAbdominal()!=null)
            warningHealths.add(babyHealthIndexService.CheckAbdominal(ga, babyHealthIndex.getAbdominal()));
        if(babyHealthIndex.getFemur()!=null)
            warningHealths.add(babyHealthIndexService.CheckFemur(ga, babyHealthIndex.getFemur()));
        babyHealthIndex.setWarningHealths(warningHealths);
        if(user.getBabyIndex() != null){
            babyHealthIndices.addAll(user.getBabyIndex());
        }
        babyHealthIndices.add(babyHealthIndex);
        user.setBabyIndex(babyHealthIndices);
        userService.update(user);
        babyHealthResponse.add(babyHealthIndex);
        return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthResponse , "success");
    }
    @PutMapping("/babyindex/update")
    public Response UpdateIndex(@RequestBody BabyHealthIndexRequest babyIndex){
        User user = userService.findAccountByID(babyIndex.getUserID());
        if(user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<BabyHealthIndex> babyHealthIndices = new ArrayList<>();
        List<BabyHealthIndex> babyHealthResponse = new ArrayList<>();
        if(user.getBabyIndex() != null){
            babyHealthIndices.addAll(user.getBabyIndex());
            babyHealthIndices.get(babyIndex.getIndex()).setHead(babyIndex.getHead());
            babyHealthIndices.get(babyIndex.getIndex()).setBiparietal(babyIndex.getBiparietal());
            babyHealthIndices.get(babyIndex.getIndex()).setOccipitofrontal(babyIndex.getOccipitofrontal());
            babyHealthIndices.get(babyIndex.getIndex()).setAbdominal(babyIndex.getAbdominal());
            babyHealthIndices.get(babyIndex.getIndex()).setFemur(babyIndex.getFemur());
            babyHealthIndices.get(babyIndex.getIndex()).setTimeUpdate(LocalDateTime.now().toString());
            List<WarningHealth> warningHealths = new ArrayList<>();
            int ga = userService.gestationalAge(user.getDatePregnant(), babyHealthIndices.get(babyIndex.getIndex()).getTimeCreate());
            if(babyHealthIndices.get(babyIndex.getIndex()).getHead()!=null)
                warningHealths.add(babyHealthIndexService.CheckHead(ga,babyHealthIndices.get(babyIndex.getIndex()).getHead()));
            if (babyHealthIndices.get(babyIndex.getIndex()).getBiparietal()!=null)
                warningHealths.add(babyHealthIndexService.CheckBiparietal(ga, babyHealthIndices.get(babyIndex.getIndex()).getBiparietal()));
            if(babyHealthIndices.get(babyIndex.getIndex()).getOccipitofrontal()!=null)
                warningHealths.add(babyHealthIndexService.CheckOccipitofrontal(ga, babyHealthIndices.get(babyIndex.getIndex()).getOccipitofrontal()));
            if (babyHealthIndices.get(babyIndex.getIndex()).getAbdominal()!=null)
                warningHealths.add(babyHealthIndexService.CheckAbdominal(ga, babyHealthIndices.get(babyIndex.getIndex()).getAbdominal()));
            if(babyHealthIndices.get(babyIndex.getIndex()).getFemur()!=null)
                warningHealths.add(babyHealthIndexService.CheckFemur(ga, babyHealthIndices.get(babyIndex.getIndex()).getFemur()));
            babyHealthIndices.get(babyIndex.getIndex()).setWarningHealths(warningHealths);
            userService.update(user);
            babyHealthResponse.add(babyHealthIndices.get(babyIndex.getIndex()));
            return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthResponse , "success");
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Index not found");
    }
    @DeleteMapping("/babyindex/delete")
    public Response DeleteIndex(@RequestBody BabyHealthIndexRequest babyIndex){
        User user = userService.findAccountByID(babyIndex.getUserID());
        if(user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<BabyHealthIndex> babyHealthIndices = new ArrayList<>();
        if(user.getBabyIndex() != null){
            babyHealthIndices.addAll(user.getBabyIndex());
            int index = babyIndex.getIndex();
            babyHealthIndices.remove(index);
            user.setBabyIndex(babyHealthIndices);
            userService.update(user);
            return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthIndices , "success");
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Index not found");

    }
    @GetMapping("/babyindex/getall")
    public Response GetIndex(@RequestParam String userID){
        User user = userService.findAccountByID(userID);
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
