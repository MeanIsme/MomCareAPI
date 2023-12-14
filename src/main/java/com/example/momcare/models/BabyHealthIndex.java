package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter

public class BabyHealthIndex {

    private Double head;
    private Double biparietal;
    private Double occipitofrontal;
    private Double abdominal;
    private Double femur;
    private String timeCreate;
    private String timeUpdate;
    private List<WarningHealth> warningHealths;

    public BabyHealthIndex(Double head, Double biparietal, Double occipitofrontal, Double abdominal, Double femur, String timeCreate, String timeUpdate) {
        this.head = head;
        this.biparietal = biparietal;
        this.occipitofrontal = occipitofrontal;
        this.abdominal = abdominal;
        this.femur = femur;
        this.timeCreate = timeCreate;
        this.timeUpdate = timeUpdate;
    }
}
