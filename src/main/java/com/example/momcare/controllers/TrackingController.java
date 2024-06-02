package com.example.momcare.controllers;

import com.example.momcare.models.Tracking;
import com.example.momcare.payload.response.Response;
import com.example.momcare.payload.response.TrackingWeekDetailResponse;
import com.example.momcare.payload.response.TrackingWeekResponse;
import com.example.momcare.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TrackingController {

    private final TrackingService service;

    public TrackingController(TrackingService service) {
        this.service = service;
    }

    @GetMapping("/trackingall")
    public Response trackingall(){
        return service.trackingall();
    }
    @GetMapping("/tracking")
    public Response trackingWeek(@RequestParam("week") int week){
        return service.trackingWeek(week);
    }
}
