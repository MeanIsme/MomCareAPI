package com.example.momcare.controllers;

import com.example.momcare.models.HandBook;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.HandBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HandBookController {

    @Autowired
    HandBookService service;
    @GetMapping("/handbookall")
    public Response findAllByCategory(@RequestParam("idCategory") String idCategory){
        return new Response(HttpStatus.OK.getReasonPhrase(), service.findHandBookByCategory(idCategory), "success");
    }

    @GetMapping("/handbook")
    public Response findByID(@RequestParam("id") String id){
        List<HandBook> handBooks = new ArrayList<>();
        handBooks.add(service.findHandBookByID(id));
        return new Response(HttpStatus.OK.getReasonPhrase(), handBooks, "success");
    }

    @GetMapping("/handbook/newest")
    public Response Top8Newest(){
        return new Response(HttpStatus.OK.getReasonPhrase(), service.Top8Newest(), "success");
    }
    @GetMapping("/handbook/random")
    public Response Random(){
        return new Response(HttpStatus.OK.getReasonPhrase(), service.Top8Random(), "success");
    }
}
