package com.trippricer.trippricermvc.controller;

import com.trippricer.trippricermvc.model.Provider;
import com.trippricer.trippricermvc.service.TripPricerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class TripPricerController {

    @Autowired
    private TripPricerService tripPricerService ;

    @RequestMapping("/getPrice")
    public List<Provider> getPrice(@RequestParam("apiKey") String apiKey,
                                   @RequestParam("attractionId") UUID attractionId,
                                   @RequestParam("adults") int adults,
                                   @RequestParam("children") int children,
                                   @RequestParam("nightsStay") int nightsStay,
                                   @RequestParam("rewardsPoints") int rewardsPoints){

        return tripPricerService.getPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints) ;
    }

}
