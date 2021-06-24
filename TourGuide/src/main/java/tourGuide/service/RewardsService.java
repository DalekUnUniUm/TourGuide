package tourGuide.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.springframework.web.client.RestTemplate;
import rewardCentral.RewardCentral;
import tourGuide.user.User;
import tourGuide.user.UserAttraction;
import tourGuide.user.UserLocation;
import tourGuide.user.UserReward;

import javax.swing.text.html.HTMLDocument;

@Service
public class RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	// proximity in miles
    private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;

	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}
	
	public void calculateRewards(User user) {
		List<UserLocation> userLocations = user.getUserLocations();
		List<UserAttraction> attractions = getAttraction();

		for(int i = 0 ; i < userLocations.size() ; i++){
			for (int j = 0 ; j < attractions.size() ; j++){
				if(nearAttraction(userLocations.get(i),attractions.get(j))) {
					user.addUserReward(new UserReward(userLocations.get(i), attractions.get(j), getRewardPoint(attractions.get(j))));
				}
			}
		}
	}
	public List<UserAttraction> getAttraction(){
        String url = "http://localhost:9001/getAttractions";
        RestTemplate restTemplate = new RestTemplate();
        String reponse = restTemplate.getForObject(url,String.class);
        Type attractionListType = new TypeToken<ArrayList<UserAttraction>>(){}.getType();
	    List<UserAttraction> attractionList = new Gson().fromJson(reponse, attractionListType);
	    return attractionList ;
    }
	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return getDistance(attraction, location) > attractionProximityRange ? false : true;
	}
	
	private boolean nearAttraction(UserLocation userLocation, UserAttraction userAttraction) {
		return getDistances(userLocation, userAttraction) > proximityBuffer ? false : true;
	}
	/**Get Reward Points from API**/
	public int getRewardPoint(UserAttraction userAttraction){
        String url = "http://localhost:9002/getAttractionRewardPoints";
        RestTemplate restTemplate = new RestTemplate();
        String reponse = restTemplate.getForObject(url,String.class);
        return Integer.valueOf(reponse) ;
	}

	public double getDistance(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
	}
    public double getDistances(UserLocation userLocation, UserAttraction userAttraction) {
        double lat1 = Math.toRadians(userLocation.getLatitude());
        double lon1 = Math.toRadians(userLocation.getLongitude());
        double lat2 = Math.toRadians(userAttraction.getLatitude());
        double lon2 = Math.toRadians(userAttraction.getLongitude());

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        double statueKilometers = statuteMiles * 1.6 ;

        return statueKilometers;
    }

}
