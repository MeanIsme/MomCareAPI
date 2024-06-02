package com.example.momcare.service;

import com.example.momcare.models.HandBook;
import com.example.momcare.payload.response.Response;
import com.example.momcare.repository.HandBookRepository;
import com.example.momcare.util.Constant;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class HandBookService {
    HandBookRepository handBookRepository;


    public HandBookService(HandBookRepository handBookRepository) {
        this.handBookRepository = handBookRepository;

    }

    public Response findHandBookByCategoryService(String idCategory) {
        List<HandBook> handBooks = findHandBookByCategory(idCategory);
        return new Response(HttpStatus.OK.getReasonPhrase(), handBooks, Constant.SUCCESS);
    }

    public Response findHandBookByIDService(String id) {
        HandBook handBook = findHandBookByID(id);
        if (handBook != null) {
            List<HandBook> handBooks = new ArrayList<>();
            handBooks.add(handBook);
            return new Response(HttpStatus.OK.getReasonPhrase(), handBooks, Constant.SUCCESS);
        } else {
            return new Response(HttpStatus.NOT_FOUND.getReasonPhrase(), new ArrayList<>(), Constant.HAND_BOOK_NOT_FOUND);
        }
    }

    public Response getTop8NewestHandBooksService() {
        List<HandBook> top8NewestHandBooks = top8Newest();
        return new Response(HttpStatus.OK.getReasonPhrase(), top8NewestHandBooks, Constant.SUCCESS);
    }
    public Response getHandBookPerPage(int time) {
        List<HandBook> handBooksPerPage = handBookPerTime(time);
        return new Response(HttpStatus.OK.getReasonPhrase(), handBooksPerPage, Constant.SUCCESS);
    }
    public Response getTop8RandomHandBooks() {
        List<HandBook> top8RandomHandBooks = top8Random();
        return new Response(HttpStatus.OK.getReasonPhrase(), top8RandomHandBooks, Constant.SUCCESS);
    }
    public Response searchHandBookByKey(String key) {
        List<HandBook> searchResult = searchHandBook(key);
        return new Response(HttpStatus.OK.getReasonPhrase(), searchResult, Constant.SUCCESS);
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
