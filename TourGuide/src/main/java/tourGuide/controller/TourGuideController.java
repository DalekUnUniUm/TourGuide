package tourGuide.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jsoniter.output.JsonStream;

import gpsUtil.location.VisitedLocation;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tourGuide.user.UserLocation;
import tourGuide.user.UserPreferences;
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
    public String getNearbyAttractions(@RequestParam("user") String userName) {
        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
    	UserLocation userLocation = tourGuideService.trackUserLocations(user);
    	return JsonStream.serialize(tourGuideService.getFiveClosestAttraction(userLocation));
    }
    @SuppressWarnings("unused")
    @RequestMapping("/getRewards") 
    public String getRewards(@RequestParam String userName) {
    	return JsonStream.serialize(tourGuideService.getUserRewards(getUser(userName)));
    }
    @SuppressWarnings("unused")
    @RequestMapping("/getAllCurrentLocations")
    public String getAllCurrentLocations() {
    	// TODO: Get a list of every user's most recent location as JSON
    	//- Note: does not use gpsUtil to query for their current location, 
    	//        but rather gathers the user's current location from their stored location history.
    	//
    	// Return object should be the just a JSON mapping of userId to Locations similar to:
    	//     {
    	//        "019b04a9-067a-4c76-8817-ee75088c3822": {"longitude":-48.188821,"latitude":74.84371} 
    	//        ...
    	//     }
    	
    	return JsonStream.serialize("");
    }
    @SuppressWarnings("unused")
    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {
    	List<Provider> providers = tourGuideService.getTripDeals(getUser(userName));
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