package tourGuide.user;

public class RecommandedAttraction {

    private String attractionName ;
    private double userLatLoc ;
    private double userLongLoc ;
    private double attractionLatLoc ;
    private double attractionLongLoc ;
    private double distance ;
    private int rewardPoint ;

    public RecommandedAttraction (String attractionName, double userLatLoc, double userLongLoc, double attractionLatLoc, double attractionLongLoc, double distance, int rewardPoint){
        this.attractionName = attractionName ;
        this.userLatLoc = userLatLoc ;
        this.userLongLoc = userLongLoc ;
        this.attractionLatLoc = attractionLatLoc ;
        this.attractionLongLoc = attractionLongLoc ;
        this.distance = distance ;
        this.rewardPoint = rewardPoint ;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public double getUserLatLoc() {
        return userLatLoc;
    }

    public double getUserLongLoc() {
        return userLongLoc;
    }

    public double getAttractionLatLoc() {
        return attractionLatLoc;
    }

    public double getAttractionLongLoc() {
        return attractionLongLoc;
    }

    public double getDistance() {
        return distance;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }
}
