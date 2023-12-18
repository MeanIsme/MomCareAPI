package com.example.momcare.controllers;

import com.example.momcare.models.PeriodicCheck;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.PeriodicCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PeriodicCheckController {
    @Autowired
    PeriodicCheckService periodicCheckService;

    @GetMapping("/periodiccheck/all")
    public Response GetAll (){
        List<PeriodicCheck> periodicCheckList = periodicCheckService.findAll();
        return new Response(HttpStatus.OK.getReasonPhrase(), periodicCheckList , "success");
    }
    @GetMapping("/periodiccheck")
    public Response GetByWeek (@RequestParam int weekFrom){
        PeriodicCheck periodicCheck = periodicCheckService.findByWeekFrom(weekFrom);
        List<PeriodicCheck> periodicCheckList = new ArrayList<>();
        periodicCheckList.add(periodicCheck);
        return new Response(HttpStatus.OK.getReasonPhrase(), periodicCheckList , "success");
    }


}
