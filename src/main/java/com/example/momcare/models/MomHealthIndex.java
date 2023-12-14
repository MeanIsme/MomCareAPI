package com.example.momcare.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class MomHealthIndex{
        private Double height;
        private Double weight;
        private Double HATT;
        private Double HATTr;
        private Double GIHungry;
        private Double GIFull1h;
        private Double GIFull2h;
        private Double GIFull3h;
        private String timeCreate;
        private String timeUpdate;
        private List<WarningHealth> warningHealths;

        public MomHealthIndex(Double height, Double weight, Double HATT, Double HATTr, Double GIHungry, Double GIFull1h, Double GIFull2h, Double GIFull3h, String timeCreate, String timeUpdate) {
                this.height = height;
                this.weight = weight;
                this.HATT = HATT;
                this.HATTr = HATTr;
                this.GIHungry = GIHungry;
                this.GIFull1h = GIFull1h;
                this.GIFull2h = GIFull2h;
                this.GIFull3h = GIFull3h;
                this.timeCreate = timeCreate;
                this.timeUpdate = timeUpdate;
        }
}
