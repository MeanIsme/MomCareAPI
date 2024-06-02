package com.example.momcare.controllers;

import com.example.momcare.models.Menu;
import com.example.momcare.payload.response.MenuDetailResponse;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MenuController {

    MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    @GetMapping("/menu")
    public Response findMenuByCategory(@RequestParam("idCategory") String idCategory) {
        return service.findMenusByCategory(idCategory);
    }

    @GetMapping("/detailmenu")
    public Response findMenuById(@RequestParam("id") String id) {
        return service.findMenuById(id);
    }
}
