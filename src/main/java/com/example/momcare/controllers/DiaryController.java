package com.example.momcare.controllers;

import com.example.momcare.models.Diary;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.DiaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DiaryController {
    @Autowired
    private DiaryService service;
    @PostMapping ("/diary/new")
    public Response CreateDiary(@RequestBody Diary diaryRequest){
        Diary diary = new Diary(diaryRequest.getIdUser(),diaryRequest.getTitle(), diaryRequest.getContent(), diaryRequest.getThumbnail(), diaryRequest.getReaction(), LocalDateTime.now().toString(),LocalDateTime.now().toString());
        if(service.save(diary))
            return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
        else
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
    }

    @GetMapping("/diary/all")
    public Response FindAllDiaryByUser(@RequestParam String idUser){
        return new Response((HttpStatus.OK.getReasonPhrase()), service.findDiaryByIdUser(idUser), "success");
    }
    @GetMapping("/diary")
    public Response FindDiaryById(@RequestParam String id){
        List<Diary> diaries = new ArrayList<>();
        diaries.add(service.findDiaryById(id));
        return new Response((HttpStatus.OK.getReasonPhrase()), diaries, "success");
    }


    @PutMapping("/diary/update")
    public Response UpdateDiary(@RequestBody Diary diaryRequest){
        Diary diary = service.findDiaryById(diaryRequest.getId());
        if (diary != null)
        {
            List<Diary> diaries = new ArrayList<>();
            diaryRequest.setTimeUpdate(LocalDateTime.now().toString());
            diaries.add(diaryRequest);
            if(service.save(diaryRequest))

                return new Response((HttpStatus.OK.getReasonPhrase()), diaries, "success");
            else
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
        }
        else
            return new Response((HttpStatus.NOT_FOUND.getReasonPhrase()), new ArrayList<>(), "failure");
    }
    @DeleteMapping("/diary/delete")
    public Response DeleteDiary(@RequestParam String id){
        Diary diary = service.findDiaryById(id);
        if (diary != null)
        {
            if(service.delete(diary))

                return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), "success");
            else
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "failure");
        }
        else
            return new Response((HttpStatus.NOT_FOUND.getReasonPhrase()), new ArrayList<>(), "failure");
    }
}
