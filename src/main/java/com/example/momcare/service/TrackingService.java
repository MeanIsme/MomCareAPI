package com.example.momcare.service;

import com.example.momcare.models.Tracking;
import com.example.momcare.payload.response.TrackingWeekDetailResponse;
import com.example.momcare.payload.response.TrackingWeekResponse;
import com.example.momcare.repository.TrackingRepository;
import com.example.momcare.util.Constant;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackingService {
    TrackingRepository trackingRepository;

    public TrackingService (TrackingRepository trackingRepository){
        this.trackingRepository = trackingRepository;
    }

    public List<TrackingWeekResponse> trackingAll(){
        List<Tracking> trackings = findAll();
        List<TrackingWeekResponse> weekResponses = new ArrayList<>();
        for (Tracking tracking : trackings) {
            weekResponses.add(new TrackingWeekResponse(tracking.getId(),  tracking.getWeek(), Constant.WEEK_BLANK + tracking.getWeek(), Constant.TRACKING));
        }
        return weekResponses;
    }
    public List<TrackingWeekDetailResponse> trackingWeek(int week){
        Tracking tracking = findTrackingByWeek(week);
        TrackingWeekDetailResponse response = new TrackingWeekDetailResponse(tracking.getId(),
                tracking.getWeek(),Constant.CONTENT_OF_WEEK_BLANK + tracking.getWeek(),
                tracking.getBaby(), tracking.getMom(), tracking.getAdvice(), tracking.getThumbnails());
        List<TrackingWeekDetailResponse> responses = new ArrayList<>();
        responses.add(response);
        return responses;
    }
    public Tracking findTrackingByWeek(int week){return this.trackingRepository.findTrackingByWeek(week);}
    public List<Tracking> findAll(){
        Sort sort = Sort.by(Sort.Direction.ASC, Constant.WEEK);
        return trackingRepository.findAll(sort);
    }
}
