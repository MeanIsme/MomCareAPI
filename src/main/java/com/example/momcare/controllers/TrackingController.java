package com.example.momcare.controllers;

import com.example.momcare.models.Tracking;
import com.example.momcare.service.AccountService;
import com.example.momcare.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackingController {

    @Autowired
    private TrackingService service;
    @PutMapping("/tracking/{week}")
    public Tracking tracking(@PathVariable("week") int week){
        Tracking tracking = service.findTrackingByWeek(week);
        return tracking;
    }
}
