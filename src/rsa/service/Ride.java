package rsa.service;
import java.lang.String;
import java.util.Comparator;
import java.util.UUID;

import rsa.quad.HasPoint;
import rsa.shared.Car;
import rsa.shared.Location;
import rsa.shared.PreferredMatch;
import rsa.shared.RideRole;
import rsa.shared.RideMatchInfo;

/**
 * <b>Class Ride</b>
 * <br>This class provides a comparator of ride matches adjusted to this ride. Ride matches are sent to clients as RideMatchInfo instances. 
 * If more than one is available then they are sorted. The order depends on ride that is being matched. <br>
 * To produce comparators this class is a concrete participant of the Factory Method design pattern.
 * It implements the RideMatchInfoSorter interface and subclasses Comparator
 * @author João Lucas Pires, Sara Ferreira
 */
public class Ride implements HasPoint, RideMatchInfoSorter {
	private float cost;
	private User user;
	private String plate;
	private Location from;
	private Location to;
	private Matcher.RideMatch match;
	private Location current;
	private PreferredMatch preferredMatch;
	private long id;
	
	/**
	 * Ride constructor. The Current location value starts as the initial location.
	 * @param user (User)
	 * @param from (Location)
	 * @param to (Location)
	 * @param plate (String)
	 * @param cost (float)
	 */
	public Ride(User user, Location from, Location to, String plate, float cost) {
		this.user = user;
		this.cost = cost;	
		this.from = from;
		this.to = to;
		this.plate = plate;
		this.cost = cost;
		this.current = from;
		this.match = null;
		if (user != null)
			if (!user.hasCar(plate))
				this.user.addCar(new Car(plate,"","",""));
		this.setId();
	}

	public void setId() {
		this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
	}
	public long getId() {
		return this.id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPlate() {
		return this.plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public boolean isDriver() {
		if(this.plate != null)
			return true;
		return false;
	}

	public RideRole getRideRole() {
		if(isDriver())
			return RideRole.DRIVER;
		return RideRole.PASSENGER;
	}

	public boolean isPassenger() {
		if(isDriver())
			return false;
		return true;
	}

	public float getCost() {
		return this.cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public Location getFrom() {
		return this.from;
	}

	public void setFrom(Location from) {
		this.from = from;
	}

	public Location getTo() {
		return this.to;
	}

	public void setTo(Location to) {
		this.to = to;
	}

	public Location getCurrent() {
		return this.current;
	}

	public void setCurrent(Location current) {
		this.current = current;
	}

	public Matcher.RideMatch getMatch() {
		return match;

	}

	public void setMatch(Matcher.RideMatch _match) {
		match = _match;
	}

	public boolean isMatched() {
		if(match != null)
			return true;
		return false;
	}

	public double getX() {
		return this.current.getX();
	}

	public double getY() {
		return this.current.getY();
	}
	
	/**
	 * Retrieves a Comparator element for RideMatchInfo instances.
	 * @return Comparator of RideMatchInfo elements
	 */
	public Comparator<RideMatchInfo> getComparator() {
		PreferredMatch myPreferredMatch = this.user.getPreferredMatch();
		switch(myPreferredMatch) {

		case BETTER:
			return new Comparator<RideMatchInfo>() {
				@Override
				public int compare(RideMatchInfo left, RideMatchInfo right) {
					float starsOne = left.getStars(left.getUserRole().other());
					float starsTwo = right.getStars(right.getUserRole().other());
					if (starsOne > starsTwo)
						return -1;
					else if (starsOne < starsTwo)
						return 1;
					else
						return 0;
				}
			};
		case CHEAPER:
			return new Comparator<RideMatchInfo>() {
				@Override
				public int compare(RideMatchInfo left, RideMatchInfo right) {
					if (left.getUserRole().equals(RideRole.PASSENGER)) {
						float costOne = left.getCost();
						float costTwo = right.getCost();
						if (costOne < costTwo)
							return -1;
						else if (costOne > costTwo)
							return 1;
						else
							return 0;
					}
					return 0;
				}
			};
		case CLOSER:
			return new Comparator<RideMatchInfo>() {
				@Override
				public int compare(RideMatchInfo left, RideMatchInfo right) {

					float distanceOne = left.calculateDistance();
					float distanceTwo = right.calculateDistance();
					if (distanceOne < distanceTwo)
						return -1;
					else if ( distanceOne > distanceTwo)
						return 1;
					else
						return 0;
				}
			};
		default:
			return null;
		}

	}

	public PreferredMatch getPreferredMatch() {
		return preferredMatch;
	}

	public void setPreferredMatch(PreferredMatch preferredMatch) {
		this.preferredMatch = preferredMatch;
	}
}
