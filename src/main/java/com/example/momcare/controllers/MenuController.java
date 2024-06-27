package com.example.momcare.controllers;

import com.example.momcare.payload.response.Response;
import com.example.momcare.service.MenuService;
import com.example.momcare.util.Constant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class MenuController {

    MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    @GetMapping("/menu")
    public Response findMenuByCategory(@RequestParam("idCategory") String idCategory) {
        return new Response(HttpStatus.OK.getReasonPhrase(), service.findMenusByCategory(idCategory), Constant.SUCCESS);
    }

    @GetMapping("/detailmenu")
    public Response findMenuById(@RequestParam("id") String id) {
        try {
            return new Response(HttpStatus.OK.getReasonPhrase(),  service.findMenuById(id), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }

    }
}
