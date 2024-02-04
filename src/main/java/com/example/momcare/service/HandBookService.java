package com.example.momcare.service;

import com.example.momcare.models.HandBook;
import com.example.momcare.repository.HandBookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class HandBookService {
    HandBookRepository handBookRepository;


    public HandBookService(HandBookRepository handBookRepository) {
        this.handBookRepository = handBookRepository;

    }

    public List<HandBook> findHandBookByCategory(String id){
        return this.handBookRepository.findAllByCategory(id);
    }
    public HandBook findHandBookByID(String id){
        return this.handBookRepository.getHandBookById(id);
    }

    public List<HandBook> Top8Newest(){
        Sort sort = Sort.by(Sort.Direction.DESC, "time");
        return handBookRepository.findAll(sort).stream()
                .limit(8)
                .collect(Collectors.toList());
    }

    public List<HandBook> searchHandBook(String keyWord)
    {
        return this.handBookRepository.findByTitleLike(keyWord);
    }


    public List<HandBook> Top8Random() {
        List<HandBook> allHandBook = handBookRepository.findAll();
        int totalHandBook = allHandBook.size();
        if (totalHandBook <= 8) {
            return allHandBook;
        }
        Random random = new Random();
        int[] indexes = random.ints(0, totalHandBook).distinct().limit(8).toArray();

        List<HandBook> randomDocuments = new ArrayList<>();
        for (int index : indexes) {
            randomDocuments.add(allHandBook.get(index));
        }

        return randomDocuments;
    }
}
