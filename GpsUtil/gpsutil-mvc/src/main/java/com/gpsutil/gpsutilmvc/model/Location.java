package com.gpsutil.gpsutilmvc.model;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class Location {

    private UUID userId ;

    private double latitude ;

    private double longitude ;

    private Date timeLocation ;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getTimeLocation() {
        return timeLocation;
    }

    public void setTimeLocation(Date timeLocation) {
        this.timeLocation = timeLocation;
    }
}
