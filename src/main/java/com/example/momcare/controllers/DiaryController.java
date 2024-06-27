package com.example.momcare.controllers;

import com.example.momcare.exception.ResourceNotFoundException;
import com.example.momcare.models.Diary;
import com.example.momcare.payload.request.DiaryRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.DiaryService;

import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

@RequestMapping("/diary")
public class DiaryController {
    private final DiaryService service;

    public DiaryController(DiaryService service) {
        this.service = service;
    }

    @PostMapping ("/new")
    public Response createDiary(@RequestBody DiaryRequest diaryRequest){
        try {
            List<Diary> diaries = List.of(service.createDiary(diaryRequest));
            return new Response(HttpStatus.OK.getReasonPhrase(), diaries, Constant.SUCCESS);
        }catch (ResourceNotFoundException e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @GetMapping("/all")
    public Response findAllDiaryByUser(@RequestParam String idUser){
        return new Response(HttpStatus.OK.getReasonPhrase(), service.findAllDiaryByUser(idUser), Constant.SUCCESS);
    }
    @GetMapping()
    public Response findDiaryById(@RequestParam String id){
        try{
            List<Diary> diaries = service.findDiaryByIdService(id);
            return new Response(HttpStatus.OK.getReasonPhrase(), diaries, Constant.SUCCESS);
        } catch (ResourceNotFoundException e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }

    @PutMapping("/update")
    public Response updateDiary(@RequestBody DiaryRequest diaryRequest){
        try {
            List<Diary> diaries = service.updateDiary(diaryRequest);
            return new Response(HttpStatus.OK.getReasonPhrase(), diaries, Constant.SUCCESS);
        } catch (ResourceNotFoundException e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }
    @DeleteMapping("/delete")
    public Response deleteDiary(@RequestParam String id){
        try {
            service.deleteDiary(id);
            return new Response(HttpStatus.OK.getReasonPhrase(), new ArrayList<>(), Constant.SUCCESS);
        } catch (ResourceNotFoundException e) {
            return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), e.getMessage());
        }
    }
    @GetMapping("/newest")
    public Response top8Newest(){
        return new Response(HttpStatus.OK.getReasonPhrase(), service.getTop8NewestDiaries(), Constant.SUCCESS);
    }
    @GetMapping("/page")
    public Response diaryPerPage(int time){
        return new Response(HttpStatus.OK.getReasonPhrase(), service.diaryPerPage(time), Constant.SUCCESS);
    }
    @GetMapping("/random")
    public Response random(){
        return new Response(HttpStatus.OK.getReasonPhrase(), service.getTop8NewestDiaries(), Constant.SUCCESS);
    }
}
