package com.example.momcare.controllers;

import com.example.momcare.payload.response.Response;
import com.example.momcare.service.MenuCategoryService;

import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuCategoryController {

    MenuCategoryService service;

    public MenuCategoryController(MenuCategoryService service) {
        this.service = service;
    }

    @GetMapping("/menuCategory")
    public Response findAll(){
        return new Response(HttpStatus.OK.getReasonPhrase(), service.findAllMenuCategory(), Constant.SUCCESS);
    }
}
