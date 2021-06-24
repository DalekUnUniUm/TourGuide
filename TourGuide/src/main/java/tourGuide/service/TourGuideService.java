package tourGuide.service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.springframework.web.client.RestTemplate;
import tourGuide.helper.InternalTestHelper;
import tourGuide.tracker.Tracker;
import tourGuide.user.*;
import tourGuide.utils.RecommandedAttractionDistanceComparator;
import tripPricer.Provider;
import tripPricer.TripPricer;

@Service
public class TourGuideService {
	private Logger logger = LoggerFactory.getLogger(TourGuideService.class);
	private final RewardsService rewardsService;
	private final TripPricer tripPricer = new TripPricer();
	public final Tracker tracker;
	boolean testMode = true;

	public TourGuideService(RewardsService rewardsService) {
		this.rewardsService = rewardsService;
		
		if(testMode) {
			logger.info("TestMode enabled");
			logger.debug("Initializing users");
			initializeInternalUsers();
			logger.debug("Finished initializing users");
		}
		tracker = new Tracker(this);
		addShutDownHook();
	}
	
	public List<UserReward> getUserRewards(User user) {
		return user.getUserRewards();
	}
	/**NEW SERVICE FOR COMMUNICATING WITH THE API GPS UTIL!!!!!**/
	public String getUserLocationFromApi(User user){
		String url = "http://localhost:9001/getLocation?userId="+user.getUserId();
		System.out.println("url = " + url);
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(url,String.class);
		return response ;
	}
	public UserLocation trackUserLocations(User user){
		String getUserLocation = getUserLocationFromApi(user) ;
		UserLocation userLocation = new Gson().fromJson(getUserLocation,UserLocation.class);
		user.addToUserLocations(userLocation);
		rewardsService.calculateRewards(user);
		return userLocation ;
	}
	/**--------------------------------------------------------**/
	public User getUser(String userName) {
		return internalUserMap.get(userName);
	}
	
	public List<User> getAllUsers() {
		return internalUserMap.values().stream().collect(Collectors.toList());
	}
	/**Save userPreferences by Reading a JSON String**/
	public String saveUserPreference(String userPreferences){
		UserPreferences preferences = new Gson().fromJson(userPreferences,UserPreferences.class);
		return userPreferences ;
	}
	/**---------------------------------------------**/
	public void addUser(User user) {
		if(!internalUserMap.containsKey(user.getUserName())) {
			internalUserMap.put(user.getUserName(), user);
		}
	}
	/**Trip Deal Provider FROM API Trip Pricer**/

	public List<UserProvider> getTripDeals(User user) {
		int cumulatativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
		String providerList = getPriceFromApi(user, cumulatativeRewardPoints);
		Type providerListType = new TypeToken<ArrayList<UserProvider>>(){}.getType();
		List<UserProvider> userProviders = new Gson().fromJson(providerList,providerListType);
		return userProviders;
	}
	public String getPriceFromApi(User user, int cumulatativeRewardPoints){
		String url = "http://localhost:9003/getPrice?apiKey="+tripPricerApiKey+"&attractionId="+user.getUserId()+"&adults="+user.getUserPreferences().getNumberOfAdults()+"&children="+user.getUserPreferences().getNumberOfChildren()+"&nightsStay="+user.getUserPreferences().getTripDuration()+"&rewardsPoints="+cumulatativeRewardPoints;
		System.out.println("url = " + url);
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(url,String.class);
		return response ;
	}
	/**---------------------------------------**/
	/**Closest five attractions**/
	public List<RecommandedAttraction> getAllAttraction(UserLocation userLocation){
		List<RecommandedAttraction> recommandedAttractions = new ArrayList<>();
		for (UserAttraction userAttraction : rewardsService.getAttraction()){
			recommandedAttractions.add(new RecommandedAttraction(userAttraction.getAttractionName(),userLocation.getLatitude(),userLocation.getLongitude(),userAttraction.getLatitude(),userAttraction.getLongitude(),rewardsService.getDistances(userLocation,userAttraction),rewardsService.getRewardPoint(userAttraction)));
		}
		Collections.sort(recommandedAttractions, new RecommandedAttractionDistanceComparator());
		return recommandedAttractions ;
	}
	public List<RecommandedAttraction> getFiveClosestAttraction(UserLocation userLocation){
		List<RecommandedAttraction> recommandedAttractions = getAllAttraction(userLocation);
		List<RecommandedAttraction> fiveClosestAttraction = new ArrayList<>() ;
		for(int i = 0 ; i < 5 ; i++){
			fiveClosestAttraction.add(new RecommandedAttraction(recommandedAttractions.get(i).getAttractionName(),recommandedAttractions.get(i).getUserLatLoc(),recommandedAttractions.get(i).getUserLongLoc(),recommandedAttractions.get(i).getAttractionLatLoc(),recommandedAttractions.get(i).getAttractionLongLoc(),recommandedAttractions.get(i).getDistance(),recommandedAttractions.get(i).getRewardPoint()));
		}

		return fiveClosestAttraction ;
	}
	/**------------------------**/
	public JSONArray getAllCurentLocation(){
		List<User> users = getAllUsers() ;
		JSONArray allCurrentLocation = new JSONArray();
		JSONObject object = new JSONObject();
		for(int i = 0 ; i < users.size() ; i++){
			object.put("longitude", users.get(i).getLastUserLocation().getLongitude());
			object.put("latitude", users.get(i).getLastUserLocation().getLatitude());
			allCurrentLocation.add(""+users.get(i).getUserId());
			allCurrentLocation.add(object);
		}
		return allCurrentLocation ;
	}
	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() { 
		      public void run() {
		        tracker.stopTracking();
		      } 
		    }); 
	}
	
	/**********************************************************************************
	 * 
	 * Methods Below: For Internal Testing
	 * 
	 **********************************************************************************/
	private static final String tripPricerApiKey = "test-server-api-key";
	// Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
	private final Map<String, User> internalUserMap = new HashMap<>();
	private void initializeInternalUsers() {
		IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
			String userName = "internalUser" + i;
			String phone = "000";
			String email = userName + "@tourGuide.com";
			User user = new User(UUID.randomUUID(), userName, phone, email);
			generateUserLocationHistory(user);
			
			internalUserMap.put(userName, user);
		});
		logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
	}
	
	private void generateUserLocationHistory(User user) {
		IntStream.range(0, 3).forEach(i-> {
			user.addToUserLocations(new UserLocation(user.getUserId(), generateRandomLatitude(), generateRandomLongitude(), getRandomTime()));
		});
	}
	
	private double generateRandomLongitude() {
		double leftLimit = -180;
	    double rightLimit = 180;
	    return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}
	
	private double generateRandomLatitude() {
		double leftLimit = -85.05112878;
	    double rightLimit = 85.05112878;
	    return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}
	
	private Date getRandomTime() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
	    return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}
	
}
