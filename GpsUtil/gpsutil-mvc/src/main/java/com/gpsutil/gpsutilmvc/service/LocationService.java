package com.gpsutil.gpsutilmvc.service;

import com.gpsutil.gpsutilmvc.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class LocationService {

    @Autowired
    private Location location ;

    public Location getLocation(int userId){
        location.setUserId(userId);
        location.setLatitude(ThreadLocalRandom.current().nextDouble(-85.05112878D, 85.05112878D));
        location.setLongitude(ThreadLocalRandom.current().nextDouble(-180.0D, 180.0D));
        location.setTimeLocation(new Date());

        return location;
    }
}
