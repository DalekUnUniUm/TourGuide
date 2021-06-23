package tourGuide.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import gpsUtil.location.VisitedLocation;
import tripPricer.Provider;

public class User {
	private final UUID userId;
	private final String userName;
	private String phoneNumber;
	private String emailAddress;
	private Date latestLocationTimestamp;
	private List<UserLocation> userLocations = new ArrayList<>();
	private List<UserReward> userRewards = new ArrayList<>();
	private UserPreferences userPreferences ;
	private List<Provider> tripDeals = new ArrayList<>();
	private List<UserProvider> tripDeals1 = new ArrayList<>();
	private List<RecommandedAttraction> recommandedAttractions = new ArrayList<>();
	public User(UUID userId, String userName, String phoneNumber, String emailAddress) {
		this.userId = userId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}
	
	public UUID getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setLatestLocationTimestamp(Date latestLocationTimestamp) {
		this.latestLocationTimestamp = latestLocationTimestamp;
	}
	public void addUserReward(UserReward userReward) {
		if(userRewards.stream().filter(r -> !r.userAttraction.getAttractionName().equals(userReward.userAttraction)).count() == 0) {
			userRewards.add(userReward);
		}
	}
	
	public List<UserReward> getUserRewards() {
		return userRewards;
	}
	
	public UserPreferences getUserPreferences() {
		return userPreferences;
	}
	
	public void setUserPreferences(UserPreferences userPreferences) {
		this.userPreferences = userPreferences;
	}

	
	public void setTripDeals(List<Provider> tripDeals) {
		this.tripDeals = tripDeals;
	}
	
	public List<Provider> getTripDeals() {
		return tripDeals;
	}

	/**NEW USER LOCATION EQUAL TO ANCIENT VISITED LOCATION**/
	public void addToUserLocations(UserLocation userLocation) {
		userLocations.add(userLocation);
	}
	public List<UserLocation> getUserLocations() {
		return userLocations;
	}
	public UserLocation getLastUserLocation() {
		return userLocations.get(userLocations.size() - 1);
	}

	public List<UserProvider> getTripDeals1() {
		return tripDeals1;
	}

	public void setTripDeals1(List<UserProvider> tripDeals1) {
		this.tripDeals1 = tripDeals1;
	}

	public List<RecommandedAttraction> getRecommandedAttractions() {
		return recommandedAttractions;
	}

	public void setRecommandedAttractions(List<RecommandedAttraction> recommandedAttractions) {
		this.recommandedAttractions = recommandedAttractions;
	}
	/**---------------------------------------------------**/

}
