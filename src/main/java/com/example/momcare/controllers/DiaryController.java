package com.example.momcare.controllers;

import com.example.momcare.payload.request.DiaryRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.DiaryService;

import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/diary")
public class DiaryController {
    private final DiaryService service;

    public DiaryController(DiaryService service) {
        this.service = service;
    }

    @PostMapping ("/new")
    public Response createDiary(@RequestBody DiaryRequest diaryRequest){
        return service.createDiary(diaryRequest);
    }

    @GetMapping("/all")
    public Response findAllDiaryByUser(@RequestParam String idUser){
        return service.findAllDiaryByUser(idUser);
    }
    @GetMapping()
    public Response findDiaryById(@RequestParam String id){
        return service.findDiaryByIdService(id);
    }

    @PutMapping("/update")
    public Response updateDiary(@RequestBody DiaryRequest diaryRequest){
        return service.updateDiary(diaryRequest);
    }
    @DeleteMapping("/delete")
    public Response deleteDiary(@RequestParam String id){
        return service.deleteDiary(id);
    }
    @GetMapping("/newest")
    public Response top8Newest(){
        return service.getTop8NewestDiaries();
    }
    @GetMapping("/page")
    public Response diaryPerPage(int time){
        return service.getDiaryPerPage(time);
    }
    @GetMapping("/random")
    public Response random(){
        return service.getTop8NewestDiaries();
    }
}
