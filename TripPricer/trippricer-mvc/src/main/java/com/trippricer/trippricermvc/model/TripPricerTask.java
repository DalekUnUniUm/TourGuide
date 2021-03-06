package com.trippricer.trippricermvc.model;

import com.trippricer.trippricermvc.service.TripPricerService;

import java.util.List;
import java.util.UUID;

public class TripPricerTask {

    private final UUID attractionId;
    private final String apiKey;
    private final int adults;
    private final int children;
    private final int nightsStay;

    public TripPricerTask(String apiKey, UUID attractionId, int adults, int children, int nightsStay) {
        this.apiKey = apiKey;
        this.attractionId = attractionId;
        this.adults = adults;
        this.children = children;
        this.nightsStay = nightsStay;
    }

    public List<Provider> call() throws Exception {
        return (new TripPricerService()).getPrice(this.apiKey, this.attractionId, this.adults, this.children, this.nightsStay, 5);
    }
}
