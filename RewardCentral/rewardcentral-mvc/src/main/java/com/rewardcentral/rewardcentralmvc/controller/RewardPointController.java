package com.rewardcentral.rewardcentralmvc.controller;

import com.rewardcentral.rewardcentralmvc.service.RewardPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RewardPointController {

    @Autowired
    private RewardPointService rewardPointService ;

    @GetMapping("/getAttractionRewardPoints")
    public int getAttractionRewardPoints(){
        return rewardPointService.getAttractionRewardPoints() ;
    }

}
