package com.example.momcare.controllers;

import com.example.momcare.models.MomHealthIndex;
import com.example.momcare.models.User;
import com.example.momcare.models.WarningHealth;
import com.example.momcare.payload.request.BabyHealthIndexRequest;
import com.example.momcare.payload.request.MomHealthIndexRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.MomHealthIndexService;
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
    private UserService userService;
    @Autowired
    private MomHealthIndexService momHealthIndexService;

    @PostMapping("/momindex/new")
    public Response CreateIndex(@RequestBody MomHealthIndexRequest momIndex) {
        User user = userService.findAccountByID(momIndex.getUserID());
        if (user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<MomHealthIndex> momHealthIndices = new ArrayList<>();
        List<MomHealthIndex> momHealthResponse = new ArrayList<>();
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
        List<WarningHealth> warningHealths = new ArrayList<>();
        if (momHealthIndex.getWeight() != null && momHealthIndex.getHeight() != null)
            warningHealths.add(momHealthIndexService.checkBMI(momHealthIndex.getHeight(), momHealthIndex.getWeight()));
        if (momHealthIndex.getHATT() != null && momHealthIndex.getHATTr() != null)
            warningHealths.add(momHealthIndexService.checkHealthRate(momHealthIndex.getHATT(), momHealthIndex.getHATTr()));
        if (momHealthIndex.getGIHungry() != null && momHealthIndex.getGIFull1h() != null && momHealthIndex.getGIFull2h() != null && momHealthIndex.getGIFull3h() != null)
            warningHealths.add(momHealthIndexService.checkGlycemicIndex(momHealthIndex.getGIHungry(), momHealthIndex.getGIFull1h(), momHealthIndex.getGIFull2h(), momHealthIndex.getGIFull3h()));
        if (user.getMomIndex() != null) {
            momHealthIndices.addAll(user.getMomIndex());
        }
        momHealthIndex.setWarningHealths(warningHealths);
        momHealthIndices.add(momHealthIndex);
        user.setMomIndex(momHealthIndices);
        userService.update(user);
        momHealthResponse.add(momHealthIndex);
        return new Response(HttpStatus.OK.getReasonPhrase(), momHealthResponse, "success");
    }

    @PutMapping("/momindex/update")
    public Response UpdateIndex(@RequestBody MomHealthIndexRequest momIndex) {
        User user = userService.findAccountByID(momIndex.getUserID());
        if (user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<MomHealthIndex> momHealthIndices = new ArrayList<>();
        List<MomHealthIndex> momHealthResponse = new ArrayList<>();
        if (user.getMomIndex() != null) {
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
            List<WarningHealth> warningHealths = new ArrayList<>();
            if (momHealthIndices.get(momIndex.getIndex()).getWeight() != null && momHealthIndices.get(momIndex.getIndex()).getHeight() != null)
                warningHealths.add(momHealthIndexService.checkBMI(momHealthIndices.get(momIndex.getIndex()).getHeight(), momHealthIndices.get(momIndex.getIndex()).getWeight()));
            if (momHealthIndices.get(momIndex.getIndex()).getHATT() != null && momHealthIndices.get(momIndex.getIndex()).getHATTr() != null)
                warningHealths.add(momHealthIndexService.checkHealthRate(momHealthIndices.get(momIndex.getIndex()).getHATT(), momHealthIndices.get(momIndex.getIndex()).getHATTr()));
            if (momHealthIndices.get(momIndex.getIndex()).getGIHungry() != null && momHealthIndices.get(momIndex.getIndex()).getGIFull1h() != null && momHealthIndices.get(momIndex.getIndex()).getGIFull2h() != null && momHealthIndices.get(momIndex.getIndex()).getGIFull3h() != null)
                warningHealths.add(momHealthIndexService.checkGlycemicIndex(momHealthIndices.get(momIndex.getIndex()).getGIHungry(), momHealthIndices.get(momIndex.getIndex()).getGIFull1h(), momHealthIndices.get(momIndex.getIndex()).getGIFull2h(), momHealthIndices.get(momIndex.getIndex()).getGIFull3h()));
            momHealthIndices.get(momIndex.getIndex()).setWarningHealths(warningHealths);
            user.setMomIndex(momHealthIndices);
            userService.update(user);
            momHealthResponse.add(momHealthIndices.get(momIndex.getIndex()));
            return new Response(HttpStatus.OK.getReasonPhrase(), momHealthResponse, "success");
        }


        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Index not found");
    }

    @PutMapping("/momindex/delete")
    public Response DeleteIndex(@RequestBody MomHealthIndexRequest momIndex) {
        User user = userService.findAccountByID(momIndex.getUserID());
        if (user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<MomHealthIndex> momHealthIndices = new ArrayList<>();
        if (user.getMomIndex() != null) {
            momHealthIndices.addAll(user.getMomIndex());
            int index = momIndex.getIndex();
            momHealthIndices.remove(index);
            user.setMomIndex(momHealthIndices);
            userService.update(user);
            return new Response(HttpStatus.OK.getReasonPhrase(), momHealthIndices, "success");
        }


        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Index not found");
    }

    @GetMapping("/momindex/getall")
    public Response GetIndex(@RequestParam String userID) {
        User user = userService.findAccountByID(userID);
        if (user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User not found");
        List<MomHealthIndex> momHealthIndices = new ArrayList<>();
        if (user.getMomIndex() != null) {
            momHealthIndices.addAll(user.getMomIndex());
            return new Response(HttpStatus.OK.getReasonPhrase(), momHealthIndices, "success");
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "Index not found");
    }

}
