package com.example.momcare.service;

import com.example.momcare.models.Category;

import com.example.momcare.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll(){return this.categoryRepository.findAll();}

}
