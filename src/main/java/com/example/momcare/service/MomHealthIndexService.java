package com.example.momcare.service;

import com.example.momcare.models.WarningHealth;
import org.springframework.stereotype.Service;

@Service
public class MomHealthIndexService {
    private WarningHealth CheckBMI(Double height, Double weight){
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName("BMI");
        double BMI = height/Math.pow(weight,2);
        if(BMI < 18.5){
            warningHealth.setType("less");
            warningHealth.setLevel(1);
        }
        else if(18.5 <= BMI && BMI < 23) {
            warningHealth.setType("normal");
            warningHealth.setLevel(1);
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
    private WarningHealth checkHealthRate(Double HATT, Double HATTr){
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
}
