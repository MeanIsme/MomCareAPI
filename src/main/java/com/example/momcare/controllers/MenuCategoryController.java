package com.example.momcare.controllers;

import com.example.momcare.models.MenuCategory;
import com.example.momcare.service.MenuCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuCategoryController {
    @Autowired
    MenuCategoryService service;
    @GetMapping("/menuCategory")
    public List<MenuCategory> findAll(){return service.findAll();}
}
