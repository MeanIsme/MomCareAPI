package com.example.momcare.service;

import com.example.momcare.models.Category;

import com.example.momcare.repository.HandBookCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandBookCategoryService {
    HandBookCategoryRepository handBookCategoryRepository;

    public HandBookCategoryService(HandBookCategoryRepository handBookCategoryRepository) {
        this.handBookCategoryRepository = handBookCategoryRepository;
    }

    public List<Category> findAll(){return this.handBookCategoryRepository.findAll();}

}
