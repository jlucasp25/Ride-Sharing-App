package rsa.service;
import java.io.Serializable;

import java.lang.String;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import rsa.shared.Car;
import rsa.shared.PreferredMatch;
import rsa.shared.RideRole;
import rsa.shared.UserStars;

/**
 * <b>Class User</b>
 * <br>An user of the Ride Sharing App. An instance of this class records the user's authentication and other relevant data.
 * @author João Lucas Pires, Sara Ferreira
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nick;
	private String password;
	private String name;
	private Map<Long,Ride> rides; 
	private Map<RideRole,LinkedList<UserStars>> stars; 
	private Map<String,Car> cars; 
	private PreferredMatch preferredMatch;

	public User(String nick, String name, String password) {
		this.nick = nick;
		this.name = name;
		this.password = password;
		this.rides = new HashMap<Long,Ride>();
		this.stars = new HashMap<RideRole,LinkedList<UserStars>>();
		this.cars = new HashMap<String,Car>();
		this.preferredMatch = PreferredMatch.BETTER;
	}

	String getNick() {
		return this.nick;
	}

	String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addCar(Car car) {
		cars.put(car.getPlate(),car);
		return;
	}
	
	public boolean hasCar(String plate) {
		if (cars.containsKey(plate))
			return true;
		return false;
	}
	
	public Car getCar(String plate) {
		if (!cars.containsKey(plate)) {
			System.out.println("The user doesn't have any car assigned with the specified plate! Created new!");
			return null;
		}
		return cars.get(plate);
	}

	void deleteCar(String plate) {
		cars.remove(plate);
		return;
	}

	public void addStars(UserStars moreStars, RideRole role) {
		if (!stars.containsKey(role)) {
			stars.put(role, new LinkedList<UserStars>());
			stars.get(role).add(moreStars);
			return;
		}
		stars.get(role).add(moreStars);
		return;
	}

	public float getAverage(RideRole role) {
		if (!stars.containsKey(role))
			return (float) 0.0;
		float current = (float) 0.0;
		for (UserStars aux : stars.get(role) ) {
			current += aux.getStars();
		}
		return (float) current / (stars.get(role).size());
	}

	boolean authenticate(String password) {
		if (this.password.equals(password))
			return true;
		return false;
	}

	PreferredMatch getPreferredMatch() {
		if (this.preferredMatch == null)
			return PreferredMatch.BETTER;
		return this.preferredMatch;
	}
	/**
	 * Sets the PreferredMatch type. If the preferredMatch is null, it will switch to the default value.
	 * @param preferredMatch (PreferredMatch)
	 */
	void setPreferredMatch(PreferredMatch preferredMatch) {
		this.preferredMatch = preferredMatch;
		if (preferredMatch == null)
			this.preferredMatch = PreferredMatch.BETTER;
	}
	
	public String getName() {
		return this.name;
	}

	public Map<Long,Ride> getRides() {
		return rides;
	}

	public void setRides(Map<Long,Ride> rides) {
		this.rides = rides;
	}
}

