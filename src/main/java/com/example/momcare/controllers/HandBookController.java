package com.example.momcare.controllers;

import com.example.momcare.payload.response.Response;
import com.example.momcare.service.HandBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HandBookController {


    HandBookService service;

    public HandBookController(HandBookService service) {
        this.service = service;
    }

    @GetMapping("/handbookall")
    public Response findAllByCategory(@RequestParam("idCategory") String idCategory) {
        return service.findHandBookByCategoryService(idCategory);
    }

    @GetMapping("/handbook")
    public Response findByID(@RequestParam("id") String id) {
        return service.findHandBookByIDService(id);
    }

    @GetMapping("/handbook/newest")
    public Response top8Newest() {
        return service.getTop8NewestHandBooksService();
    }

    @GetMapping("/handbook/page")
    public Response handBookPerPage(@RequestParam int time) {
        return service.getHandBookPerPage(time);
    }

    @GetMapping("/handbook/random")
    public Response random() {
        return service.getTop8RandomHandBooks();
    }

    @GetMapping("/handbook/search")
    public Response search(@RequestParam("key") String key) {
        return service.searchHandBookByKey(key);
    }

}
