package com.example.momcare.controllers;

import com.example.momcare.models.MenuCategory;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.MenuCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuCategoryController {

    MenuCategoryService service;

    public MenuCategoryController(MenuCategoryService service) {
        this.service = service;
    }

    @GetMapping("/menuCategory")
    public Response findAll(){
        return service.findAllMenuCategory();
    }
}
