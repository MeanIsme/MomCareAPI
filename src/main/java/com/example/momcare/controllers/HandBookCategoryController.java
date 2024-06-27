package com.example.momcare.controllers;

import com.example.momcare.payload.response.Response;
import com.example.momcare.service.HandBookCategoryService;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HandBookCategoryController {

    private final HandBookCategoryService service;

    public HandBookCategoryController(HandBookCategoryService service) {
        this.service = service;
    }

    @GetMapping("/category")
    public Response findAll(){
        return new Response(HttpStatus.OK.getReasonPhrase(), service.findAllCategories(), Constant.SUCCESS);
    }
    @GetMapping("/categoryByCollection")
    public Response findCategoryByCollection(@RequestParam ("collectionId")String  collectionId){
        return new Response(HttpStatus.OK.getReasonPhrase(), service.findCategoriesByCollection(collectionId), Constant.SUCCESS);
    }
}
