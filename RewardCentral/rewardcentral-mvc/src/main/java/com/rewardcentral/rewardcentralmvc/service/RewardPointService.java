package com.rewardcentral.rewardcentralmvc.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class RewardPointService {

    public int getAttractionRewardPoints(){

        int randomInt = ThreadLocalRandom.current().nextInt(1, 1000);
        return randomInt;
    }

}
