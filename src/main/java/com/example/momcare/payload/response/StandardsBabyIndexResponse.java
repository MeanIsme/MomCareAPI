package com.example.momcare.payload.response;

import com.example.momcare.models.StandardsIndex;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardsBabyIndexResponse {
    private StandardsIndex head;
    private StandardsIndex biparietal;
    private StandardsIndex occipitofrontal;
    private StandardsIndex abdominal;
    private StandardsIndex femur;
}
