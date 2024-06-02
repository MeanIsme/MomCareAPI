package com.example.momcare.controllers;

import com.example.momcare.payload.request.MomHealthIndexRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.MomHealthIndexService;
import com.example.momcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class MomHealthIndexController {


    private final MomHealthIndexService momHealthIndexService;

    public MomHealthIndexController(MomHealthIndexService momHealthIndexService) {
        this.momHealthIndexService = momHealthIndexService;
    }

    @PostMapping("/momindex/new")
    public Response createIndex(@RequestBody MomHealthIndexRequest momIndex) {
        return momHealthIndexService.createMomHealthIndex(momIndex);
    }

    @PutMapping("/momindex/update")
    public Response updateIndex(@RequestBody MomHealthIndexRequest momIndex) {
        return momHealthIndexService.updateMomHealthIndex(momIndex);
    }

    @PutMapping("/momindex/delete")
    public Response deleteIndex(@RequestBody MomHealthIndexRequest momIndex) {
        return momHealthIndexService.deleteMomHealthIndex(momIndex);
    }

    @GetMapping("/momindex/getall")
    public Response getIndex(@RequestParam String userID) {
        return momHealthIndexService.getMomHealthIndex(userID);
    }

    @GetMapping("/momindex/standardsindex")
    public Response getIndex() {
        return momHealthIndexService.getIndexStandard();
    }

}
