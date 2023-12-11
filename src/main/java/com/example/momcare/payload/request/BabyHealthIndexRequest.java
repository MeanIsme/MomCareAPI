package com.example.momcare.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BabyHealthIndexRequest {
    private String userID;
    private Integer index;
    private Double head;
    private Double biparietal;
    private Double occipitofrontal;
    private Double abdominal;
    private Double femur;
}
