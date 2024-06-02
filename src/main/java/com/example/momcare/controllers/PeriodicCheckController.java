package com.example.momcare.controllers;

import com.example.momcare.payload.response.Response;
import com.example.momcare.service.PeriodicCheckService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeriodicCheckController {

    PeriodicCheckService periodicCheckService;

    public PeriodicCheckController(PeriodicCheckService periodicCheckService) {
        this.periodicCheckService = periodicCheckService;
    }

    @GetMapping("/periodiccheck/all")
    public Response getAll (){
        return periodicCheckService.getAll();
    }
    @GetMapping("/periodiccheck")
    public Response getByWeek (@RequestParam int weekFrom){
        return periodicCheckService.findByWeekFromService(weekFrom);
    }


}
