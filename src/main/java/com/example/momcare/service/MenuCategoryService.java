package com.example.momcare.service;

import com.example.momcare.models.MenuCategory;
import com.example.momcare.repository.MenuCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuCategoryService {
    MenuCategoryRepository repository;

    public MenuCategoryService(MenuCategoryRepository repository) {
        this.repository = repository;
    }
    public List<MenuCategory> findAllMenuCategory() {
        return findAll();
    }
    public List<MenuCategory> findAll(){return this.repository.findAll();}

}
