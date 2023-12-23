package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardsIndex {
    private Double min;
    private Double max;

    public StandardsIndex(Double max) {
        this.max = max;
    }

    public StandardsIndex(Double min, Double max) {
        this.min = min;
        this.max = max;
    }
}
