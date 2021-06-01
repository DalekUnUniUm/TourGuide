package com.gpsutil.gpsutilmvc.controller;

import com.gpsutil.gpsutilmvc.model.Location;
import com.gpsutil.gpsutilmvc.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class LocationController {

    @Autowired
    private LocationService locationService ;

    @GetMapping("/getLocation")
    public Location getLocation(@RequestParam("userId") UUID userId){
        return locationService.getLocation(userId) ;
    }

}
