package tourGuide.controller;

import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jsoniter.output.JsonStream;

import gpsUtil.location.VisitedLocation;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tourGuide.user.UserLocation;
import tourGuide.user.UserPreferences;
import tourGuide.user.UserProvider;
import tripPricer.Provider;

@RestController
public class TourGuideController {

	@Autowired
	TourGuideService tourGuideService;
	
    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }
    
    /**@RequestMapping("/getLocation")
    public String getLocation(@RequestParam String userName) {
    	VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
		return JsonStream.serialize(visitedLocation.location);
    }**/
    @SuppressWarnings("unused")
    @RequestMapping("/getLocation")
    public String getLocation(@RequestParam String userName) {
        return tourGuideService.getUserLocationFromApi(getUser(userName));
    }
    @SuppressWarnings("unused")
    @RequestMapping("/getNearbyAttractions")
    public String getNearbyAttractions(String userName) {
    	UserLocation userLocation = tourGuideService.trackUserLocations(getUser(userName));
    	return JsonStream.serialize(tourGuideService.getFiveClosestAttraction(userLocation));
    }
    @SuppressWarnings("unused")
    @RequestMapping("/getRewards") 
    public String getRewards(@RequestParam String userName) {
    	return JsonStream.serialize(tourGuideService.getUserRewards(getUser(userName)));
    }
    @SuppressWarnings("unused")
    @RequestMapping("/getAllCurrentLocations")
    public JSONArray getAllCurrentLocations(User user) {
    	return tourGuideService.getAllCurentLocation();
    }
    @SuppressWarnings("unused")
    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {
    	List<UserProvider> providers = tourGuideService.getTripDeals(getUser(userName));
    	return JsonStream.serialize(providers);
    }
    
    private User getUser(String userName) {
    	return tourGuideService.getUser(userName);
    }
    @SuppressWarnings("unused")
    @PostMapping("/saveUserPreference")
    public String saveUserPreference(@RequestBody String userPreferences){
        return tourGuideService.saveUserPreference(userPreferences) ;
    }
   

}