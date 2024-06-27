package com.example.momcare.service;

import com.example.momcare.exception.ResourceNotFoundException;
import com.example.momcare.models.HandBook;
import com.example.momcare.repository.HandBookRepository;
import com.example.momcare.util.Constant;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class HandBookService {
    HandBookRepository handBookRepository;


    public HandBookService(HandBookRepository handBookRepository) {
        this.handBookRepository = handBookRepository;

    }

    public List<HandBook> findHandBookByCategoryService(String idCategory) {
        return findHandBookByCategory(idCategory);
    }

    public List<HandBook> findHandBookByIDService(String id) throws ResourceNotFoundException {
        HandBook handBook = findHandBookByID(id);
        if (handBook != null) {
            List<HandBook> handBooks = new ArrayList<>();
            handBooks.add(handBook);
            return handBooks;
        } else {
            throw new ResourceNotFoundException(Constant.HAND_BOOK_NOT_FOUND);
        }
    }

    public List<HandBook> getTop8NewestHandBooksService() {
        return top8Newest();

    }
    public List<HandBook> getHandBookPerPage(int time) {
        return handBookPerTime(time);

    }
    public List<HandBook> getTop8RandomHandBooks() {
        return top8Random();

    }
    public List<HandBook> searchHandBookByKey(String key) {
        return searchHandBook(key);

    }
    public List<HandBook> findHandBookByCategory(String id){
        return this.handBookRepository.findAllByCategory(id);
    }
    public HandBook findHandBookByID(String id){
        return this.handBookRepository.getHandBookById(id);
    }

    public List<HandBook> top8Newest(){
            Sort sort = Sort.by(Sort.Direction.DESC, Constant.TIME);
            return handBookRepository.findAll(sort).stream()
                    .limit(8)
                    .toList();
    }
    public List<HandBook> handBookPerTime(int time){
        Sort sort = Sort.by(Sort.Direction.DESC, Constant.TIME);
        return handBookRepository.findAll(sort).stream()
                .skip(time* 20L)
                .toList();
    }
    public List<HandBook>  searchHandBook(String keyWord)
    {
        return this.handBookRepository.findByTitleLike(keyWord);
    }


    public List<HandBook> top8Random() {
        List<HandBook> allHandBook = handBookRepository.findAll();
        int totalHandBook = allHandBook.size();
        if (totalHandBook <= 8) {
            return allHandBook;
        }
        int[] indexes = new Random().ints(0, totalHandBook).distinct().limit(8).toArray();

        List<HandBook> randomDocuments = new ArrayList<>();
        for (int index : indexes) {
            randomDocuments.add(allHandBook.get(index));
        }

        return randomDocuments;
    }
}
