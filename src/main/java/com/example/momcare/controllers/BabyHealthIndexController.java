package com.example.momcare.controllers;

import com.example.momcare.payload.request.BabyHealthIndexRequest;
import com.example.momcare.payload.request.StandIndexRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.BabyHealthIndexService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/babyindex")
public class BabyHealthIndexController {
    private final BabyHealthIndexService babyHealthIndexService;

    public BabyHealthIndexController(BabyHealthIndexService babyHealthIndexService) {
        this.babyHealthIndexService = babyHealthIndexService;
    }

    @PostMapping("/new")
    public Response createIndex(@RequestBody BabyHealthIndexRequest babyIndex) {
        return babyHealthIndexService.createBabyHealthIndex(babyIndex);
    }

    @PutMapping("/update")
    public Response updateIndex(@RequestBody BabyHealthIndexRequest babyIndex) {
        return babyHealthIndexService.updateBabyHealthIndex(babyIndex);
    }

    @PutMapping("/delete")
    public Response deleteIndex(@RequestBody BabyHealthIndexRequest babyIndex) {
        return babyHealthIndexService.deleteBabyHealthIndex(babyIndex);
    }

    @GetMapping("/getall")
    public Response getIndex(@RequestParam String userID) {
        return babyHealthIndexService.getAllBabyHealthIndices(userID);
    }

    @PutMapping("/standardsindex")
    public Response getstandardIndex(@RequestBody StandIndexRequest request) {
        return babyHealthIndexService.getStandardBabyIndex(request.getDatePregnant(), request.getDateEnd());
    }
}