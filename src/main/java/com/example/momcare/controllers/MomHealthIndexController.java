package com.example.momcare.controllers;

import com.example.momcare.models.MomHealthIndex;
import com.example.momcare.models.User;
import com.example.momcare.payload.request.BabyHealthIndexRequest;
import com.example.momcare.payload.request.MomHealthIndexRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MomHealthIndexController {
    @Autowired
    private UserService service;
    @PostMapping("/momindex/new")
    public Response CreateIndex(@RequestBody MomHealthIndexRequest momIndex){
        User user = service.findAccountByID(momIndex.getUserID());
        if(user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<MomHealthIndex> momHealthIndices = new ArrayList<>();
        MomHealthIndex momHealthIndex = new MomHealthIndex(
                momIndex.getHeight(),
                momIndex.getWeight(),
                momIndex.getHATT(),
                momIndex.getHATTr(),
                momIndex.getGIHungry(),
                momIndex.getGIFull1h(),
                momIndex.getGIFull2h(),
                momIndex.getGIFull3h(),
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString()
        );
        if(user.getMomIndex() != null){
            momHealthIndices.addAll(user.getMomIndex());
        }
        momHealthIndices.add(momHealthIndex);
        user.setMomIndex(momHealthIndices);
        service.update(user);
        return new Response(HttpStatus.OK.getReasonPhrase(), momHealthIndices , "success");
    }
    @PutMapping("/momindex/update")
    public Response UpdateIndex(@RequestBody MomHealthIndexRequest momIndex){
        User user = service.findAccountByID(momIndex.getUserID());
        if(user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<MomHealthIndex> momHealthIndices = new ArrayList<>();
        if(user.getMomIndex() != null){
            momHealthIndices.addAll(user.getMomIndex());
            momHealthIndices.get(momIndex.getIndex()).setHeight(momIndex.getHeight());
            momHealthIndices.get(momIndex.getIndex()).setWeight(momIndex.getWeight());
            momHealthIndices.get(momIndex.getIndex()).setHATT(momIndex.getHATT());
            momHealthIndices.get(momIndex.getIndex()).setHATTr(momIndex.getHATTr());
            momHealthIndices.get(momIndex.getIndex()).setGIHungry(momIndex.getGIHungry());
            momHealthIndices.get(momIndex.getIndex()).setGIFull1h(momIndex.getGIFull1h());
            momHealthIndices.get(momIndex.getIndex()).setGIFull2h(momIndex.getGIFull2h());
            momHealthIndices.get(momIndex.getIndex()).setGIFull3h(momIndex.getGIFull3h());
            momHealthIndices.get(momIndex.getIndex()).setTimeUpdate(LocalDateTime.now().toString());
            user.setMomIndex(momHealthIndices);
            service.update(user);
            return new Response(HttpStatus.OK.getReasonPhrase(), momHealthIndices , "success");
        }


        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Index not found");
    }
    @DeleteMapping("/momindex/delete")
    public Response DeleteIndex(@RequestBody MomHealthIndexRequest momIndex){
        User user = service.findAccountByID(momIndex.getUserID());
        if(user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<MomHealthIndex> momHealthIndices = new ArrayList<>();
        if(user.getMomIndex() != null){
            momHealthIndices.addAll(user.getMomIndex());
            int index = momIndex.getIndex();
            momHealthIndices.remove(index);
            user.setMomIndex(momHealthIndices);
            service.update(user);
            return new Response(HttpStatus.OK.getReasonPhrase(), momHealthIndices , "success");
        }


        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Index not found");
    }
}
