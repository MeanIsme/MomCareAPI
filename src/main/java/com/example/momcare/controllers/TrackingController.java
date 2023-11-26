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

    @Autowired
    private TrackingService service;
    @GetMapping("/trackingall")
    public Response trackingall(){
        List<Tracking> trackings = service.findAll();
        List<TrackingWeekResponse> weekResponses = new ArrayList<>();
        for (Tracking tracking : trackings) {
            weekResponses.add(new TrackingWeekResponse(tracking.getId(),  tracking.getWeek(), "Week " + tracking.getWeek(), "tracking"));
        }
        return new Response(HttpStatus.OK.getReasonPhrase(), weekResponses , "success");
    }
    @GetMapping("/tracking")
    @ResponseBody
    public Response trackingWeek(@RequestParam("week") int week){
        Tracking tracking = service.findTrackingByWeek(week);
        TrackingWeekDetailResponse response = new TrackingWeekDetailResponse(tracking.getId(),
                tracking.getWeek(),"Content of week " + tracking.getWeek(),
                tracking.getBaby(), tracking.getMom(), tracking.getAdvice(), tracking.getThumbnails());
        List<TrackingWeekDetailResponse> responses = new ArrayList<>();
        responses.add(response);
        return new Response(HttpStatus.OK.getReasonPhrase(), responses, "success");
    }


}
