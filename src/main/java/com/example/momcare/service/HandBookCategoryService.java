package com.example.momcare.service;

import com.example.momcare.models.HandBookCategory;

import com.example.momcare.payload.response.Response;
import com.example.momcare.repository.HandBookCategoryRepository;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandBookCategoryService {
    HandBookCategoryRepository handBookCategoryRepository;

    public HandBookCategoryService(HandBookCategoryRepository handBookCategoryRepository) {
        this.handBookCategoryRepository = handBookCategoryRepository;
    }

    public Response findAllCategories() {
        List<HandBookCategory> categories = this.findAll();
        return new Response(HttpStatus.OK.getReasonPhrase(), categories, Constant.SUCCESS);
    }

    public Response findCategoriesByCollection(String collectionId) {
        List<HandBookCategory> categories = this.findCategoryByCollection(collectionId);
        return new Response(HttpStatus.OK.getReasonPhrase(), categories, Constant.SUCCESS);
    }
    public List<HandBookCategory> findAll(){return this.handBookCategoryRepository.findAll();}
    public List<HandBookCategory> findCategoryByCollection(String collection){
        return this.handBookCategoryRepository.findByCollectionIn(collection);
    }

}
