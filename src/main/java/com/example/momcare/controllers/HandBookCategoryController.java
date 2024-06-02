package com.example.momcare.controllers;

import com.example.momcare.payload.response.Response;
import com.example.momcare.service.HandBookCategoryService;
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

        return service.findAllCategories();
    }
    @GetMapping("/categoryByCollection")
    public Response findCategoryByCollection(@RequestParam ("collectionId")String  collectionId){

        return service.findCategoriesByCollection(collectionId);
    }
}
