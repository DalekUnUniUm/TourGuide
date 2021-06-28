package tourGuide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.junit.Ignore;
import org.junit.Test;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.*;
import tripPricer.Provider;

public class TestTourGuideService {

	private TourGuideService tourGuideService ;

	private void setUpPerTest(int numberOfInternalUserNumber) {
		RewardsService rewardsService = new RewardsService();
		InternalTestHelper.setInternalUserNumber(numberOfInternalUserNumber);
		tourGuideService = new TourGuideService(rewardsService);
	}

	/**New Test With The API GPS UTIL**/
	@Test
	public void getUserLocation(){
		setUpPerTest(0);
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		UserLocation userLocation = tourGuideService.trackUserLocations(user);
		tourGuideService.tracker.stopTracking();
		assertTrue(userLocation.getUserId().equals(user.getUserId()));

	}
	@Test
	public void trackUser(){
		setUpPerTest(0);
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		UserLocation userLocation = tourGuideService.trackUserLocations(user);
		tourGuideService.tracker.stopTracking();
		assertEquals(user.getUserId(),userLocation.getUserId());
	}
	/**------------------------------**/
	@Test
	public void addUser() {
		setUpPerTest(0);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		
		User retrivedUser = tourGuideService.getUser(user.getUserName());
		User retrivedUser2 = tourGuideService.getUser(user2.getUserName());

		tourGuideService.tracker.stopTracking();
		
		assertEquals(user, retrivedUser);
		assertEquals(user2, retrivedUser2);
	}
	
	@Test
	public void getAllUsers() {
		setUpPerTest(0);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		
		List<User> allUsers = tourGuideService.getAllUsers();

		tourGuideService.tracker.stopTracking();
		
		assertTrue(allUsers.contains(user));
		assertTrue(allUsers.contains(user2));
	}
	@Test
	public void getNearbyAttractions() {
		setUpPerTest(0);
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		UserLocation userLocation = tourGuideService.trackUserLocations(user);
		
		List<RecommandedAttraction> attractions = tourGuideService.getFiveClosestAttraction(userLocation);
		System.out.println("FIVE CLOSEST");
		 for(RecommandedAttraction rA : attractions){
		 System.out.println("Distance = " + rA.getDistance());
		 }
		tourGuideService.tracker.stopTracking();
		
		assertEquals(5, attractions.size());
	}
	@Test
	public void getTripDeals() {
		setUpPerTest(0);

		UserPreferences userPreferences = new UserPreferences(5,"jon",2,4,2,2);
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		user.setUserPreferences(userPreferences);


		List<UserProvider> providers = tourGuideService.getTripDeals(user);
		
		tourGuideService.tracker.stopTracking();
		assertEquals(10, providers.size());
	}
	@Test
	public void getAllCurentLocation(){
		setUpPerTest(100);
		JSONArray allCurentLocation = new JSONArray();
		allCurentLocation = tourGuideService.getAllCurentLocation();
		assertEquals(200, allCurentLocation.size());
	}
}
