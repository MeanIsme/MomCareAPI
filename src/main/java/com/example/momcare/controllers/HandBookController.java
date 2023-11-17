package com.example.momcare.controllers;

import com.example.momcare.payload.response.Response;
import com.example.momcare.service.CollectionService;
import com.example.momcare.service.HandBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HandBookController {

    @Autowired
    HandBookService service;
    @GetMapping("/handbook")
    public Response findAllByCategory(@RequestParam("id") String id){
        return new Response(HttpStatus.OK.getReasonPhrase(), service.findHandBookByCategory(id), "success");
    }

}
