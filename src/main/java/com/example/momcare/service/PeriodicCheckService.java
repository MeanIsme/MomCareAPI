package com.example.momcare.service;

import com.example.momcare.models.PeriodicCheck;
import com.example.momcare.payload.response.Response;
import com.example.momcare.repository.PeriodicCheckRepository;
import com.example.momcare.util.Constant;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeriodicCheckService {
    PeriodicCheckRepository periodicCheckRepository;

    public PeriodicCheckService(PeriodicCheckRepository periodicCheckRepository) {
        this.periodicCheckRepository = periodicCheckRepository;
    }

    public Response getAll (){
        List<PeriodicCheck> periodicCheckList = findAll();
        return new Response(HttpStatus.OK.getReasonPhrase(), periodicCheckList, Constant.SUCCESS);
    }
    public Response findByWeekFromService(int weekFrom) {
        PeriodicCheck periodicCheck = findByWeekFrom(weekFrom);
        List<PeriodicCheck> periodicCheckList = new ArrayList<>();
        periodicCheckList.add(periodicCheck);
        return new Response(HttpStatus.OK.getReasonPhrase(), periodicCheckList , Constant.SUCCESS);
    }
    public List<PeriodicCheck> findAll(){
        Sort sort = Sort.by(Sort.Direction.ASC, Constant.WEEK_FROM);
        return this.periodicCheckRepository.findAll(sort);
    }
    public PeriodicCheck findByWeekFrom(int weekFrom){
        return this.periodicCheckRepository.findByWeekFrom(weekFrom);
    }
}
