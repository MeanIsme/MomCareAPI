package com.example.momcare.controllers;
import com.example.momcare.payload.response.Response;
import com.example.momcare.service.TrackingService;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrackingController {

    private final TrackingService service;

    public TrackingController(TrackingService service) {
        this.service = service;
    }

    @GetMapping("/trackingall")
    public Response trackingall(){
        return new Response(HttpStatus.OK.getReasonPhrase(), service.trackingAll(), Constant.SUCCESS);
    }
    @GetMapping("/tracking")
    public Response trackingWeek(@RequestParam("week") int week){
        return new Response(HttpStatus.OK.getReasonPhrase(), service.trackingWeek(week), Constant.SUCCESS);
    }
}
