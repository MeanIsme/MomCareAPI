package com.example.momcare.controllers;

import com.example.momcare.payload.response.Response;
import com.example.momcare.service.HandBookService;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class HandBookController {


    HandBookService service;

    public HandBookController(HandBookService service) {
        this.service = service;
    }

    @GetMapping("/handbookall")
    public Response findAllByCategory(@RequestParam("idCategory") String idCategory) {
        return new Response(HttpStatus.OK.getReasonPhrase(), service.findHandBookByCategoryService(idCategory), Constant.SUCCESS);
    }

    @GetMapping("/handbook")
    public Response findByID(@RequestParam("id") String id) {
        try{
            return new Response(HttpStatus.OK.getReasonPhrase(), service.findHandBookByIDService(id), Constant.SUCCESS);
        } catch (Exception e) {
            return new Response(HttpStatus.NOT_FOUND.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @GetMapping("/handbook/newest")
    public Response top8Newest() {
        return new Response(HttpStatus.OK.getReasonPhrase(), service.getTop8NewestHandBooksService(), Constant.SUCCESS);
    }

    @GetMapping("/handbook/page")
    public Response handBookPerPage(@RequestParam int time) {
        return new Response(HttpStatus.OK.getReasonPhrase(), service.getHandBookPerPage(time), Constant.SUCCESS);
    }

    @GetMapping("/handbook/random")
    public Response random() {
        return new Response(HttpStatus.OK.getReasonPhrase(),  service.getTop8RandomHandBooks(), Constant.SUCCESS);
    }

    @GetMapping("/handbook/search")
    public Response search(@RequestParam("key") String key) {
        return new Response(HttpStatus.OK.getReasonPhrase(),  service.searchHandBookByKey(key), Constant.SUCCESS);
    }

}
