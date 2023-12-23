package com.example.momcare.service;

import com.example.momcare.models.StandardsIndex;
import com.example.momcare.models.WarningHealth;
import com.example.momcare.payload.response.StandardsMomIndexResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MomHealthIndexService {
    public WarningHealth checkBMI(Double height, Double weight){
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName("BMI");
        double BMI = weight/Math.pow(height,2);
        if(BMI < 18.5){
            warningHealth.setType("less");
            warningHealth.setLevel(1);
        }
        else if(18.5 <= BMI && BMI < 23) {
            warningHealth.setType("normal");
            warningHealth.setLevel(0);
        }
        else if(23 <= BMI && BMI <= 24.9){
            warningHealth.setType("over");
            warningHealth.setLevel(1);
        }
        else{
            warningHealth.setType("over");
            warningHealth.setLevel(2);
        }
        return warningHealth;
    }
    public WarningHealth checkHealthRate(Double HATT, Double HATTr){
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName("HealthRate");
        if(HATT < 85 || HATTr <60){
            warningHealth.setType("less");
            warningHealth.setLevel(1);
        }
        else if (85 <=HATT && HATT < 90){
            warningHealth.setType("less");
            warningHealth.setLevel(0);
        }
        else if (90 <=HATT && HATT <= 130 && 60<=HATTr && HATTr <= 90){
            warningHealth.setType("normal");
            warningHealth.setLevel(0);
        }
        else if (130 <HATT && HATT <= 139 && 85<=HATTr && HATTr <= 90) {
            warningHealth.setType("over");
            warningHealth.setLevel(0);
        }
        else if (140 <= HATT && HATT <= 159 && 90<HATTr && HATTr <= 99) {
            warningHealth.setType("over");
            warningHealth.setLevel(1);
        }
        else if (160 <= HATT && HATT <= 179 && 100<=HATTr && HATTr <= 109) {
            warningHealth.setType("over");
            warningHealth.setLevel(2);
        }
        else{
            warningHealth.setType("over");
            warningHealth.setLevel(3);
        }


        return warningHealth;
    }
    public WarningHealth checkGlycemicIndex(Double GIHungry, Double GIFull1h, Double GIFull2h, Double GIFull3h){
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName("GlycemicIndex");
        int count = 0;
        if(GIHungry >= 95)
            count++;
        if(GIFull1h >= 180)
            count++;
        if(GIFull2h >= 155 )
            count++;
        if(GIFull3h >= 140)
            count++;
        if(count > 2){
            warningHealth.setType("over");
            warningHealth.setLevel(1);
        }
        else {
            warningHealth.setType("normal");
            warningHealth.setLevel(0);
        }
        return warningHealth;
    }

    public StandardsMomIndexResponse GetStandardMomIndex(){
        StandardsMomIndexResponse standardsIndex = new StandardsMomIndexResponse();
        standardsIndex.setBMI(new StandardsIndex(18.5, 23.0));
        standardsIndex.setHATT(new StandardsIndex(90.0,130.0));
        standardsIndex.setHATTr(new StandardsIndex(60.0,90.0));
        standardsIndex.setGIHungry(new StandardsIndex(95.0));
        standardsIndex.setGIFull1h(new StandardsIndex(180.0));
        standardsIndex.setGIFull2h(new StandardsIndex(155.0));
        standardsIndex.setGIFull3h(new StandardsIndex(140.0));
        return standardsIndex;
    }
}
