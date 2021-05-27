package com.gpsutil.gpsutilmvc.model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Location {

    private int userId ;

    private double latitude ;

    private double longitude ;

    private Date timeLocation ;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
