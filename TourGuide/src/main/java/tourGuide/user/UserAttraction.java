package tourGuide.user;

import java.util.UUID;

public class UserAttraction {

    private String attractionName ;
    private String city ;
    private String state ;
    private double latitude ;
    private double longitude ;
    private UUID attractionId ;

    public UserAttraction(String attractionName, String city, String state, double latitude, double longitude){
        this.attractionName = attractionName ;
        this.city = city ;
        this.state = state ;
        this.latitude = latitude ;
        this.longitude = longitude ;
        this.attractionId = UUID.randomUUID();
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public UUID getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(UUID attractionId) {
        this.attractionId = attractionId;
    }

}
