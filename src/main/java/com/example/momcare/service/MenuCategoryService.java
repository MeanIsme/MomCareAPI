package com.example.momcare.service;

import com.example.momcare.models.MenuCategory;
import com.example.momcare.payload.response.Response;
import com.example.momcare.repository.MenuCategoryRepository;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuCategoryService {
    MenuCategoryRepository repository;

    public MenuCategoryService(MenuCategoryRepository repository) {
        this.repository = repository;
    }
    public Response findAllMenuCategory() {
        List<MenuCategory> allHandBooks = findAll();
        return new Response(HttpStatus.OK.getReasonPhrase(), allHandBooks, Constant.SUCCESS);
    }
    public List<MenuCategory> findAll(){return this.repository.findAll();}

}
