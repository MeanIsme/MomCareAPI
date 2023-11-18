package com.example.momcare.controllers;

import com.example.momcare.payload.response.Response;
import com.example.momcare.service.HandBookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HandBookCategoryController {
    @Autowired
    private HandBookCategoryService service;
    @GetMapping("/category")
    public Response findAll(){

        return new Response(HttpStatus.OK.getReasonPhrase(),service.findAll(), "success");
    }
    @GetMapping("/categoryByCollection")
    public Response findCategoryByCollection(@RequestParam ("collectionId")String  collectionId){

        return new Response(HttpStatus.OK.getReasonPhrase(),service.findCategoryByCollection(collectionId), "success");
    }
}
