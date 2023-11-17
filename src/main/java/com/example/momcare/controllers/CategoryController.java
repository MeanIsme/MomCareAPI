package com.example.momcare.controllers;

import com.example.momcare.payload.response.Response;
import com.example.momcare.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService service;
    @GetMapping("/category")
    public Response findAll(){

        return new Response(HttpStatus.OK.getReasonPhrase(),service.findAll(), "success");
    }

}
