package com.example.momcare.service;

import com.example.momcare.models.HandBookCollection;
import com.example.momcare.payload.response.Response;
import com.example.momcare.repository.HandBookCollectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandBookCollectionService {
    HandBookCollectionRepository repository;

    public HandBookCollectionService(HandBookCollectionRepository repository) {
        this.repository = repository;
    }

    public Response findAllCollection() {
        List<HandBookCollection> diaries = repository.findAll();
        return new Response(HttpStatus.OK.getReasonPhrase(), diaries, "success");
    }
    public List<HandBookCollection> findAll(){ return this.repository.findAll();}
}
