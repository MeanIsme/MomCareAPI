package com.example.momcare.service;

import com.example.momcare.exception.ResourceNotFoundException;
import com.example.momcare.models.MomHealthIndex;
import com.example.momcare.models.StandardsIndex;
import com.example.momcare.models.User;
import com.example.momcare.models.WarningHealth;
import com.example.momcare.payload.request.MomHealthIndexRequest;
import com.example.momcare.payload.response.StandardsMomIndexResponse;
import com.example.momcare.util.Constant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MomHealthIndexService {
    UserService userService;

    public MomHealthIndexService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public List<MomHealthIndex> createMomHealthIndex(MomHealthIndexRequest momIndex) throws ResourceNotFoundException {
        User user = userService.findAccountByID(momIndex.getUserID());
        if (user == null)
            throw new ResourceNotFoundException(Constant.USER_NOT_FOUND);
        List<MomHealthIndex> momHealthIndices = new ArrayList<>();
        List<MomHealthIndex> momHealthResponse = new ArrayList<>();
        MomHealthIndex momHealthIndex = new MomHealthIndex(
                momIndex.getHeight(),
                momIndex.getWeight(),
                momIndex.getHATT(),
                momIndex.getHATTr(),
                momIndex.getGIHungry(),
                momIndex.getGIFull1h(),
                momIndex.getGIFull2h(),
                momIndex.getGIFull3h(),
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString()
        );
        List<WarningHealth> warningHealths = new ArrayList<>();
        if (momHealthIndex.getWeight() != null && momHealthIndex.getHeight() != null)
            warningHealths.add(checkBMI(momHealthIndex.getHeight(), momHealthIndex.getWeight()));
        if (momHealthIndex.getHATT() != null && momHealthIndex.getHATTr() != null)
            warningHealths.add(checkHealthRate(momHealthIndex.getHATT(), momHealthIndex.getHATTr()));
        if (momHealthIndex.getGIHungry() != null && momHealthIndex.getGIFull1h() != null && momHealthIndex.getGIFull2h() != null && momHealthIndex.getGIFull3h() != null)
            warningHealths.add(checkGlycemicIndex(momHealthIndex.getGIHungry(), momHealthIndex.getGIFull1h(), momHealthIndex.getGIFull2h(), momHealthIndex.getGIFull3h()));
        if (user.getMomIndex() != null) {
            momHealthIndices.addAll(user.getMomIndex());
        }
        momHealthIndex.setWarningHealths(warningHealths);
        momHealthIndices.add(momHealthIndex);
        user.setMomIndex(momHealthIndices);
        userService.update(user);
        momHealthResponse.add(momHealthIndex);
        return momHealthResponse;

    }

    @Transactional
    public List<MomHealthIndex> updateMomHealthIndex(MomHealthIndexRequest momIndex) throws ResourceNotFoundException {
        User user = userService.findAccountByID(momIndex.getUserID());
        if (user == null)
            throw new ResourceNotFoundException(Constant.USER_NOT_FOUND);
        List<MomHealthIndex> momHealthResponse = new ArrayList<>();
        if (user.getMomIndex() != null) {
            List<MomHealthIndex> momHealthIndices = new ArrayList<>(user.getMomIndex());
            momHealthIndices.get(momIndex.getIndex()).setHeight(momIndex.getHeight());
            momHealthIndices.get(momIndex.getIndex()).setWeight(momIndex.getWeight());
            momHealthIndices.get(momIndex.getIndex()).setHATT(momIndex.getHATT());
            momHealthIndices.get(momIndex.getIndex()).setHATTr(momIndex.getHATTr());
            momHealthIndices.get(momIndex.getIndex()).setGIHungry(momIndex.getGIHungry());
            momHealthIndices.get(momIndex.getIndex()).setGIFull1h(momIndex.getGIFull1h());
            momHealthIndices.get(momIndex.getIndex()).setGIFull2h(momIndex.getGIFull2h());
            momHealthIndices.get(momIndex.getIndex()).setGIFull3h(momIndex.getGIFull3h());
            momHealthIndices.get(momIndex.getIndex()).setTimeUpdate(LocalDateTime.now().toString());
            List<WarningHealth> warningHealths = new ArrayList<>();
            if (momHealthIndices.get(momIndex.getIndex()).getWeight() != null && momHealthIndices.get(momIndex.getIndex()).getHeight() != null)
                warningHealths.add(checkBMI(momHealthIndices.get(momIndex.getIndex()).getHeight(), momHealthIndices.get(momIndex.getIndex()).getWeight()));
            if (momHealthIndices.get(momIndex.getIndex()).getHATT() != null && momHealthIndices.get(momIndex.getIndex()).getHATTr() != null)
                warningHealths.add(checkHealthRate(momHealthIndices.get(momIndex.getIndex()).getHATT(), momHealthIndices.get(momIndex.getIndex()).getHATTr()));
            if (momHealthIndices.get(momIndex.getIndex()).getGIHungry() != null && momHealthIndices.get(momIndex.getIndex()).getGIFull1h() != null && momHealthIndices.get(momIndex.getIndex()).getGIFull2h() != null && momHealthIndices.get(momIndex.getIndex()).getGIFull3h() != null)
                warningHealths.add(checkGlycemicIndex(momHealthIndices.get(momIndex.getIndex()).getGIHungry(), momHealthIndices.get(momIndex.getIndex()).getGIFull1h(), momHealthIndices.get(momIndex.getIndex()).getGIFull2h(), momHealthIndices.get(momIndex.getIndex()).getGIFull3h()));
            momHealthIndices.get(momIndex.getIndex()).setWarningHealths(warningHealths);
            user.setMomIndex(momHealthIndices);
            userService.update(user);
            momHealthResponse.add(momHealthIndices.get(momIndex.getIndex()));
            return momHealthResponse;
        } else {
            throw new ResourceNotFoundException(Constant.INDEX_NOT_FOUND);
        }
    }

    @Transactional
    public List<MomHealthIndex> deleteMomHealthIndex(MomHealthIndexRequest momIndex) throws ResourceNotFoundException {
        User user = userService.findAccountByID(momIndex.getUserID());
        if (user == null)
            throw new ResourceNotFoundException(Constant.USER_NOT_FOUND);
        List<MomHealthIndex> momHealthResponse = new ArrayList<>();
        if (user.getMomIndex() != null) {
            List<MomHealthIndex> momHealthIndices = new ArrayList<>(user.getMomIndex());
            momHealthIndices.remove(momIndex.getIndex());
            user.setMomIndex(momHealthIndices);
            userService.update(user);
            momHealthResponse.addAll(momHealthIndices);
            return momHealthResponse;

        }else {
            throw new ResourceNotFoundException(Constant.INDEX_NOT_FOUND);
        }
    }

    public List<MomHealthIndex> getMomHealthIndex(String userID) throws ResourceNotFoundException {
        User user = userService.findAccountByID(userID);
        if (user == null)
            throw new ResourceNotFoundException(Constant.USER_NOT_FOUND);
        if (user.getMomIndex() != null) {
            return new ArrayList<>(user.getMomIndex());

        }else {
            throw new ResourceNotFoundException(Constant.INDEX_NOT_FOUND);
        }
    }


    public WarningHealth checkBMI(Double height, Double weight) {
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName(Constant.BMI);
        double bmi = weight / Math.pow(height / 100, 2);
        if (bmi < 18.5) {
            warningHealth.setType(Constant.LESS);
            warningHealth.setLevel(1);
        } else if (18.5 <= bmi && bmi < 23) {
            warningHealth.setType(Constant.NORMAL);
            warningHealth.setLevel(0);
        } else if (23 <= bmi && bmi <= 24.9) {
            warningHealth.setType(Constant.OVER);
            warningHealth.setLevel(1);
        } else {
            warningHealth.setType(Constant.OVER);
            warningHealth.setLevel(2);
        }
        return warningHealth;
    }

    public WarningHealth checkHealthRate(Double hatt, Double hattr) {
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName(Constant.HEALTH_RATE);
        if (hatt < 85 || hattr < 60) {
            warningHealth.setType(Constant.LESS);
            warningHealth.setLevel(1);
        } else if (85 <= hatt && hatt < 90) {
            warningHealth.setType(Constant.LESS);
            warningHealth.setLevel(0);
        } else if (90 <= hatt && hatt <= 130 && 60 <= hattr && hattr <= 90) {
            warningHealth.setType(Constant.NORMAL);
            warningHealth.setLevel(0);
        } else if (130 < hatt && hatt <= 139 && 85 <= hattr && hattr <= 90) {
            warningHealth.setType(Constant.OVER);
            warningHealth.setLevel(0);
        } else if (140 <= hatt && hatt <= 159 && 90 < hattr && hattr <= 99) {
            warningHealth.setType(Constant.OVER);
            warningHealth.setLevel(1);
        } else if (160 <= hatt && hatt <= 179 && 100 <= hattr && hattr <= 109) {
            warningHealth.setType(Constant.OVER);
            warningHealth.setLevel(2);
        } else {
            warningHealth.setType(Constant.OVER);
            warningHealth.setLevel(3);
        }


        return warningHealth;
    }

    public WarningHealth checkGlycemicIndex(Double gihungry, Double gifull1H, Double gifull2H, Double gifull3H) {
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName(Constant.GLYCEMIC_INDEX);
        int count = 0;
        if (gihungry >= 95)
            count++;
        if (gifull1H >= 180)
            count++;
        if (gifull2H >= 155)
            count++;
        if (gifull3H >= 140)
            count++;
        if (count > 2) {
            warningHealth.setType(Constant.OVER);
            warningHealth.setLevel(1);
        } else {
            warningHealth.setType(Constant.NORMAL);
            warningHealth.setLevel(0);
        }
        return warningHealth;
    }

    public List<StandardsMomIndexResponse> getIndexStandard() {
        return List.of(getStandardMomIndex());
    }

    public StandardsMomIndexResponse getStandardMomIndex() {
        StandardsMomIndexResponse standardsIndex = new StandardsMomIndexResponse();
        standardsIndex.setBMI(new StandardsIndex(18.5, 23.0));
        standardsIndex.setHATT(new StandardsIndex(90.0, 130.0));
        standardsIndex.setHATTr(new StandardsIndex(60.0, 90.0));
        standardsIndex.setGIHungry(new StandardsIndex(95.0));
        standardsIndex.setGIFull1h(new StandardsIndex(180.0));
        standardsIndex.setGIFull2h(new StandardsIndex(155.0));
        standardsIndex.setGIFull3h(new StandardsIndex(140.0));
        return standardsIndex;
    }
}
