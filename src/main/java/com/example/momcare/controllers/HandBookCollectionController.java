package com.example.momcare.controllers;

import com.example.momcare.payload.response.Response;
import com.example.momcare.service.HandBookCollectionService;

import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HandBookCollectionController {
    HandBookCollectionService service;

    public HandBookCollectionController(HandBookCollectionService service) {
        this.service = service;
    }

    @GetMapping("/collections")
    public Response findAll(){
        return new Response(HttpStatus.OK.getReasonPhrase(), service.findAllCollection(), Constant.SUCCESS);
    }
}
