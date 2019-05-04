package rsa.shared;
import java.util.Map;

import rsa.quad.Trie;
import rsa.service.Matcher;
import rsa.service.Ride;

/**
 * <b>Class RideMatchInfo</b>
 * <br>The info of a match between 2 rides (driver and passenger).<br>
 * This is a DAO (Data Access Object) that provides information on current ride matches to external components.
 * @author João Lucas Pires, Sara Ferreira
 */
public class RideMatchInfo{
	private long id;
	private Map<RideRole,Ride> rides;
	private RideRole userRole;
	private PreferredMatch userPreferredMatch;
	
	public RideMatchInfo(Matcher.RideMatch match){
		this.id = match.getId();
		this.rides = match.getRides();
		this.userRole = match.getUserRole();
		this.userPreferredMatch = match.getUserPreferredMatch();
	}
	
	public Car getCar() {
		Ride ride = rides.get(RideRole.DRIVER);
		return ride.getUser().getCar(ride.getPlate());
	}
	
	public float getCost() {
		return rides.get(RideRole.DRIVER).getCost();		
	}
	
	public long getMatchId() {
		return this.id;
	}

	public String getName(RideRole role) {
		return rides.get(role).getUser().getName();
	}
	
	public float getStars(RideRole role) {
		return rides.get(role).getUser().getAverage(role);
	}
	
	public Location getWhere(RideRole role) {
		return rides.get(role).getFrom();
	}

	public RideRole getUserRole() {
		return userRole;
	}

	public void setUserRole(RideRole userRole) {
		this.userRole = userRole;
	}

	public PreferredMatch getUserPreferredMatch() {
		return userPreferredMatch;
	}

	public void setUserPreferredMatch(PreferredMatch userPreferredMatch) {
		this.userPreferredMatch = userPreferredMatch;
	}
	 
	public float calculateDistance() {
		Location left = rides.get(userRole).getCurrent();
		Location right = rides.get(userRole.other()).getCurrent();
		return (float) Trie.getDistance(left.getX(),left.getY(),right.getX(),right.getY());
	}

}
