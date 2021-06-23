package tourGuide.user;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

public class UserReward {

	public final UserLocation userLocation;
	public final UserAttraction userAttraction;
	private int rewardPoints;
	public UserReward(UserLocation userLocation, UserAttraction userAttraction, int rewardPoints) {
		this.userLocation = userLocation;
		this.userAttraction = userAttraction;
		this.rewardPoints = rewardPoints;
	}
	
	public UserReward(UserLocation userLocation, UserAttraction userAttraction) {
		this.userLocation = userLocation;
		this.userAttraction = userAttraction;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	
	public int getRewardPoints() {
		return rewardPoints;
	}
	
}
