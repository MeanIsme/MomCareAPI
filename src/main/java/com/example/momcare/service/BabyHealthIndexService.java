package com.example.momcare.service;

import com.example.momcare.models.BabyHealthIndex;
import com.example.momcare.models.User;
import com.example.momcare.models.WarningHealth;
import com.example.momcare.models.StandardsIndex;
import com.example.momcare.payload.request.BabyHealthIndexRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.payload.response.StandardsBabyIndexResponse;
import com.example.momcare.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BabyHealthIndexService {

    private final UserService userService;

    public BabyHealthIndexService(UserService userService) {
        this.userService = userService;
    }
    @Transactional
    public Response createBabyHealthIndex(BabyHealthIndexRequest babyIndex) {
        User user = userService.findAccountByID(babyIndex.getUserID());
        if (user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);

        BabyHealthIndex babyHealthIndex = createBabyHealthIndexFromRequest(babyIndex);
        babyHealthIndex.setWarningHealths(generateWarningHealths(user, babyHealthIndex));

        List<BabyHealthIndex> babyHealthIndices = user.getBabyIndex();
        if (babyHealthIndices == null) {
            babyHealthIndices = new ArrayList<>();
        }
        babyHealthIndices.add(babyHealthIndex);
        user.setBabyIndex(babyHealthIndices);
        userService.update(user);

        return new Response(HttpStatus.OK.getReasonPhrase(), List.of(babyHealthIndex), Constant.SUCCESS);
    }

    private BabyHealthIndex createBabyHealthIndexFromRequest(BabyHealthIndexRequest babyIndex) {
        return new BabyHealthIndex(
                babyIndex.getHead(),
                babyIndex.getBiparietal(),
                babyIndex.getOccipitofrontal(),
                babyIndex.getAbdominal(),
                babyIndex.getFemur(),
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString());
    }
    @Transactional
    public Response updateBabyHealthIndex(BabyHealthIndexRequest babyIndex) {
        User user = userService.findAccountByID(babyIndex.getUserID());
        if (user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
        List<BabyHealthIndex> babyHealthResponse = new ArrayList<>();
        if (user.getBabyIndex() != null) {
            List<BabyHealthIndex> babyHealthIndices = new ArrayList<>(user.getBabyIndex());
            babyHealthIndices.get(babyIndex.getIndex()).setHead(babyIndex.getHead());
            babyHealthIndices.get(babyIndex.getIndex()).setBiparietal(babyIndex.getBiparietal());
            babyHealthIndices.get(babyIndex.getIndex()).setOccipitofrontal(babyIndex.getOccipitofrontal());
            babyHealthIndices.get(babyIndex.getIndex()).setAbdominal(babyIndex.getAbdominal());
            babyHealthIndices.get(babyIndex.getIndex()).setFemur(babyIndex.getFemur());
            babyHealthIndices.get(babyIndex.getIndex()).setTimeUpdate(LocalDateTime.now().toString());
            List<WarningHealth> warningHealths = new ArrayList<>();
            int ga = userService.gestationalAge(user.getDatePregnant(), babyHealthIndices.get(babyIndex.getIndex()).getTimeCreate());
            if (babyHealthIndices.get(babyIndex.getIndex()).getHead() != null)
                warningHealths.add(checkHead(ga, babyHealthIndices.get(babyIndex.getIndex()).getHead()));
            if (babyHealthIndices.get(babyIndex.getIndex()).getBiparietal() != null)
                warningHealths.add(checkBiparietal(ga, babyHealthIndices.get(babyIndex.getIndex()).getBiparietal()));
            if (babyHealthIndices.get(babyIndex.getIndex()).getOccipitofrontal() != null)
                warningHealths.add(checkOccipitofrontal(ga, babyHealthIndices.get(babyIndex.getIndex()).getOccipitofrontal()));
            if (babyHealthIndices.get(babyIndex.getIndex()).getAbdominal() != null)
                warningHealths.add(checkAbdominal(ga, babyHealthIndices.get(babyIndex.getIndex()).getAbdominal()));
            if (babyHealthIndices.get(babyIndex.getIndex()).getFemur() != null)
                warningHealths.add(checkFemur(ga, babyHealthIndices.get(babyIndex.getIndex()).getFemur()));
            babyHealthIndices.get(babyIndex.getIndex()).setWarningHealths(warningHealths);
            userService.update(user);
            babyHealthResponse.add(babyHealthIndices.get(babyIndex.getIndex()));
            return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthResponse, Constant.SUCCESS);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.INDEX_NOT_FOUND);
    }
    @Transactional
    public Response deleteBabyHealthIndex(BabyHealthIndexRequest babyIndex) {
        User user = userService.findAccountByID(babyIndex.getUserID());
        if (user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
        if (user.getBabyIndex() != null) {
            List<BabyHealthIndex> babyHealthIndices = new ArrayList<>(user.getBabyIndex());
            int index = babyIndex.getIndex();
            babyHealthIndices.remove(index);
            user.setBabyIndex(babyHealthIndices);
            userService.update(user);
            return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthIndices, Constant.SUCCESS);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.INDEX_NOT_FOUND);
    }

    public Response getAllBabyHealthIndices(String userID) {
        User user = userService.findAccountByID(userID);
        if (user == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
        if (user.getBabyIndex() != null) {
            List<BabyHealthIndex> babyHealthIndices = new ArrayList<>(user.getBabyIndex());
            return new Response(HttpStatus.OK.getReasonPhrase(), babyHealthIndices, Constant.SUCCESS);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.INDEX_NOT_FOUND);
    }

    public Response getStandardBabyIndex(String datePregnant, String dateEnd) {
        int ga = userService.gestationalAge(datePregnant, dateEnd);
        return new Response(HttpStatus.OK.getReasonPhrase(), List.of(getStandardBabyIndex(ga)), Constant.SUCCESS);
    }

    private List<WarningHealth> generateWarningHealths(User user, BabyHealthIndex babyHealthIndex) {
        List<WarningHealth> warningHealths = new ArrayList<>();
        int ga = userService.gestationalAge(user.getDatePregnant(), babyHealthIndex.getTimeCreate());

        if (babyHealthIndex.getHead() != null)
            warningHealths.add(checkHead(ga, babyHealthIndex.getHead()));
        if (babyHealthIndex.getBiparietal() != null)
            warningHealths.add(checkBiparietal(ga, babyHealthIndex.getBiparietal()));
        if (babyHealthIndex.getOccipitofrontal() != null)
            warningHealths.add(checkOccipitofrontal(ga, babyHealthIndex.getOccipitofrontal()));
        if (babyHealthIndex.getAbdominal() != null)
            warningHealths.add(checkAbdominal(ga, babyHealthIndex.getAbdominal()));
        if (babyHealthIndex.getFemur() != null)
            warningHealths.add(checkFemur(ga, babyHealthIndex.getFemur()));

        return warningHealths;
    }

    private StandardsIndex headIndex(int ga) {
        Double mean = -28.2849 + 1.69267 * Math.pow(ga, 2) - 0.397485 * Math.pow(ga, 2) * Math.log(ga);
        Double sd = 1.98735 + 0.0136772 * Math.pow(ga, 3) - 0.00726264 * Math.pow(ga, 3) * Math.log(ga) + 0.000976253 * Math.pow(ga, 3) * Math.pow(Math.log(ga), 2);
        return new StandardsIndex(mean - sd, mean + sd);
    }

    public WarningHealth checkHead(int ga, Double head) {
        StandardsIndex headIndex = headIndex(ga);
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName(Constant.HEAD);
        if (headIndex.getMin() > head) {
            warningHealth.setType(Constant.LESS);
            warningHealth.setLevel(1);
        } else if (headIndex.getMax() < head) {
            warningHealth.setType(Constant.OVER);
            warningHealth.setLevel(1);
        } else {
            warningHealth.setType(Constant.NORMAL);
            warningHealth.setLevel(0);
        }
        return warningHealth;
    }

    private StandardsIndex biparietalIndex(int ga) {
        Double mean = 5.60878 + 0.158369 * Math.pow(ga, 2) - 0.00256379 * Math.pow(ga, 3);
        Double sd = Math.abs(0.101242 + 0.00150557 * Math.pow(ga, 3) - 0.000771535 * Math.pow(ga, 3) * Math.log(ga) + 0.0000999638 * Math.pow(ga, 3) * Math.pow(Math.log(ga), 2));
        return new StandardsIndex(mean - sd, mean + sd);
    }

    public WarningHealth checkBiparietal(int ga, Double biparietal) {
        StandardsIndex biparietalIndex = biparietalIndex(ga);
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName(Constant.BIPARIETAL);
        if (biparietalIndex.getMin() > biparietal) {
            warningHealth.setType(Constant.LESS);
            warningHealth.setLevel(1);
        } else if (biparietalIndex.getMax() < biparietal) {
            warningHealth.setType(Constant.OVER);
            warningHealth.setLevel(1);
        } else {
            warningHealth.setType(Constant.NORMAL);
            warningHealth.setLevel(0);
        }
        return warningHealth;
    }

    private StandardsIndex occipitofrontalIndex(int ga) {
        Double mean = -12.4097 + 0.626342 * Math.pow(ga, 2) - 0.148075 * Math.pow(ga, 2) * Math.log(ga);
        Double sd = Math.abs(-0.880034 + 0.0631165 * Math.pow(ga, 2) - 0.0317136 * Math.pow(ga, 2) * Math.log(ga) + 0.00408302 * Math.pow(ga, 2) * Math.pow(Math.log(ga), 2));
        return new StandardsIndex(mean - sd, mean + sd);
    }

    public WarningHealth checkOccipitofrontal(int ga, Double occipitofrontal) {
        StandardsIndex occipitofrontalIndex = occipitofrontalIndex(ga);
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName(Constant.OCCIPITOFRONTAL);
        if (occipitofrontalIndex.getMin() > occipitofrontal) {
            warningHealth.setType(Constant.LESS);
            warningHealth.setLevel(1);
        } else if (occipitofrontalIndex.getMax() < occipitofrontal) {
            warningHealth.setType(Constant.OVER);
            warningHealth.setLevel(1);
        } else {
            warningHealth.setType(Constant.NORMAL);
            warningHealth.setLevel(0);
        }
        return warningHealth;
    }

    private StandardsIndex abdominalIndex(int ga) {
        Double mean = -81.3243 + 11.6772 * ga - 0.000561865 * Math.pow(ga, 3);
        Double sd = -4.36302 + 0.121445 * Math.pow(ga, 2) - 0.0130256 * Math.pow(ga, 3) + 0.00282143 * Math.pow(ga, 3) * Math.log(ga);
        return new StandardsIndex(mean - sd, mean + sd);
    }

    public WarningHealth checkAbdominal(int ga, Double abdominal) {
        StandardsIndex abdominalIndex = abdominalIndex(ga);
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName(Constant.ABDOMINAL);
        if (abdominalIndex.getMin() > abdominal) {
            warningHealth.setType(Constant.LESS);
            warningHealth.setLevel(1);
        } else if (abdominalIndex.getMax() < abdominal) {
            warningHealth.setType(Constant.OVER);
            warningHealth.setLevel(1);
        } else {
            warningHealth.setType(Constant.NORMAL);
            warningHealth.setLevel(0);
        }
        return warningHealth;
    }

    private StandardsIndex femurIndex(int ga) {
        Double mean = -39.9616 + 4.32298 * ga - 0.0380156 * Math.pow(ga, 2);
        Double sd = Math.abs(0.605843 - 42.0014 * Math.pow(ga, -2) + 0.00000917972 * Math.pow(ga, 3));
        return new StandardsIndex(mean - sd, mean + sd);
    }

    public WarningHealth checkFemur(int ga, Double femur) {
        StandardsIndex femurIndex = femurIndex(ga);
        WarningHealth warningHealth = new WarningHealth();
        warningHealth.setName(Constant.FEMUR);
        if (femurIndex.getMin() > femur) {
            warningHealth.setType(Constant.LESS);
            warningHealth.setLevel(1);
        } else if (femurIndex.getMax() < femur) {
            warningHealth.setType(Constant.OVER);
            warningHealth.setLevel(1);
        } else {
            warningHealth.setType(Constant.NORMAL);
            warningHealth.setLevel(0);
        }
        return warningHealth;
    }

    public StandardsBabyIndexResponse getStandardBabyIndex(int ga) {
        StandardsBabyIndexResponse standardsIndex = new StandardsBabyIndexResponse();
        standardsIndex.setHead(headIndex(ga));
        standardsIndex.setAbdominal(abdominalIndex(ga));
        standardsIndex.setOccipitofrontal(occipitofrontalIndex(ga));
        standardsIndex.setBiparietal(biparietalIndex(ga));
        standardsIndex.setFemur(femurIndex(ga));
        return standardsIndex;
    }

    public List<BabyHealthIndex> updateDatePregnant(User user) {
        List<WarningHealth> warningHealths = new ArrayList<>();
        List<BabyHealthIndex> babyHealthIndices = new ArrayList<>();
        for (BabyHealthIndex babyHealthIndex : user.getBabyIndex()) {
            int ga = userService.gestationalAge(user.getDatePregnant(), babyHealthIndex.getTimeCreate());
            if (babyHealthIndex.getHead() != null)
                warningHealths.add(checkHead(ga, babyHealthIndex.getHead()));
            if (babyHealthIndex.getBiparietal() != null)
                warningHealths.add(checkBiparietal(ga, babyHealthIndex.getBiparietal()));
            if (babyHealthIndex.getOccipitofrontal() != null)
                warningHealths.add(checkOccipitofrontal(ga, babyHealthIndex.getOccipitofrontal()));
            if (babyHealthIndex.getAbdominal() != null)
                warningHealths.add(checkAbdominal(ga, babyHealthIndex.getAbdominal()));
            if (babyHealthIndex.getFemur() != null)
                warningHealths.add(checkFemur(ga, babyHealthIndex.getFemur()));
            babyHealthIndex.setWarningHealths(warningHealths);
            babyHealthIndices.add(babyHealthIndex);
        }
        return babyHealthIndices;
    }
}