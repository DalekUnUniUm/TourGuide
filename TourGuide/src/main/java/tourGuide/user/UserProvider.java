package tourGuide.user;

import java.util.UUID;

public class UserProvider {

    public String name;
    public double price;
    public UUID tripId;

    public UserProvider(UUID tripId, String name, double price) {
        this.name = name;
        this.tripId = tripId;
        this.price = price;
    }
}
