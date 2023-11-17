package com.example.momcare.service;

import com.example.momcare.models.HandBook;
import com.example.momcare.repository.HandBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandBookService {
    HandBookRepository handBookRepository;

    public HandBookService(HandBookRepository handBookRepository) {
        this.handBookRepository = handBookRepository;
    }
    public List<HandBook> findHandBookByCategory(String id){
        return this.handBookRepository.getHandBookByCategory(id);
    }
    public HandBook findHandBookByID(String id){
        return this.handBookRepository.getHandBookById(id);
    }
}
