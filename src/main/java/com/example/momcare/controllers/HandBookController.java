package com.example.momcare.controllers;

import com.example.momcare.models.HandBook;
import com.example.momcare.payload.response.HandBookDetailResponse;
import com.example.momcare.payload.response.HandBookResponse;
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
        List<HandBook> handBooks = service.findHandBookByCategory(idCategory);
        List<HandBookResponse> handBookResponses = new ArrayList<>();
        for (HandBook handBook : handBooks){
            handBookResponses.add(new HandBookResponse(handBook.getId(), handBook.getCategory(), handBook.getTitle(),handBook.getThumbnail(), handBook.getTime()));
        }
        return new Response(HttpStatus.OK.getReasonPhrase(), handBookResponses, "success");
    }

    @GetMapping("/handbook")
    public HandBookDetailResponse findByID(@RequestParam("id") String id){
        return new HandBookDetailResponse(HttpStatus.OK.getReasonPhrase(),  service.findHandBookByID(id), "success");
    }
}
