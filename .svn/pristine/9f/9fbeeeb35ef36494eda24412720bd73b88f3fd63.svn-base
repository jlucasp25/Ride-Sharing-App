package rsa.service;

import java.io.Serializable;
import java.lang.String;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import rsa.quad.PointQuadtree;
import rsa.quad.Trie;
import rsa.shared.Location;
import rsa.shared.PreferredMatch;
import rsa.shared.RideMatchInfo;
import rsa.shared.RideRole;
import rsa.shared.UserStars;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Class Matcher</b>
 * <br>An instance of this class will match pair of rides, matching occurs when rides positions are updated and use quad trees rsa.quad to locate matches currently in nearby locations. <br>
 * @see rsa.quad
 * @author João Lucas Pires, Sara Ferreira
 */
public class Matcher implements Serializable {
	private static Map<Long,Ride> rides;
	private static PointQuadtree<Location> qtree;
	private static Map<Long,RideMatch> matches;
	private static Location bottomRight;
	private static Location topLeft;
	private static double radius;
	private static final long serialVersionUID = 1L;
	
	//Default values for matching boundaries.
	private static final float TOP_LEFT_X = (float) 0.0;
	private static final float TOP_LEFT_Y = (float) 1000.0;
	private static final float BOTTOM_RIGHT_X = (float) 1000.0;
	private static final float BOTTOM_RIGHT_Y = (float) 0.0;
	private static final float DEFAULT_RADIUS = (float) 10.0;
	
	public Matcher() {
		matches = new HashMap<Long,RideMatch>();
		topLeft = new Location(TOP_LEFT_X,TOP_LEFT_Y); 
		bottomRight = new Location(BOTTOM_RIGHT_X, BOTTOM_RIGHT_Y);
		qtree = new PointQuadtree<Location>(topLeft.getX(),topLeft.getY(),bottomRight.getX(),bottomRight.getY());
		rides = new HashMap<Long,Ride>();
		radius = DEFAULT_RADIUS;
	}
	
	/**
	 * Creates and adds a new Ride to the Matcher's ride map.
	 * @param user (User)
	 * @param from (Location)
	 * @param to (Location)
	 * @param plate (String)
	 * @param cost (float)
	 * @return Newly added Ride ID (long)
	 */
	public long addRide(User user, Location from, Location to, String plate, float cost) {
		Ride newRide = new Ride(user,from,to,plate,cost);
		rides.put(newRide.getId(),newRide);
		qtree.insert(from);
		qtree.insert(to);
		return newRide.getId();
	}

	public void acceptMatch(long rideId, long matchId) {
		rides.get(rideId).setMatch(matches.get(matchId));
		return;
	}

	public void concludeRide(long rideId, UserStars stars) {
		Ride ride = rides.get(rideId);
		RideRole userRole = ride.getMatch().getUserRole();
		User starsUser = ride.getMatch().getRides().get(userRole).getUser();
		starsUser.addStars(stars, userRole);
		rides.remove(rideId);
		return;
	}

	public static Location getTopLeft() {
		return topLeft;

	}

	public static void setTopLeft(Location _topLeft) {
		topLeft = _topLeft;
		return;
		
	}

	public static Location getBottomRight() {
		return bottomRight;

	}

	public static void setBottomRight(Location _bottomRight) {
		bottomRight = _bottomRight;
		return;
	}

	public static double getRadius() {
		return radius;
	}

	public static void setRadius(double _radius) {
		radius = _radius;
		return;
	}
	/**
	 * Updates the current location of the requested ride.<br>
	 * Next, if the ride hasn't been matched, creates a sorted set of RidesMatchInfo in order to choose the best match.
	 * @param rideId (long)
	 * @param current (current)
	 * @return a SortedSet of RideMatchInfo if the specified ride hasn't been matched or null in the opposite case.*/
	SortedSet<RideMatchInfo> updateRide(long rideId, Location current) {
		Ride ride = rides.get(rideId);
		ride.setCurrent(current);
		if (!ride.isMatched()) {
			SortedSet<RideMatchInfo> set = new TreeSet<RideMatchInfo>(ride.getComparator());
			for (Ride aux : rides.values()) {
				RideMatch rm = new RideMatch(ride,aux);
				if (rm.matchable()) {
					matches.put(rm.getId(), rm);
					RideMatchInfo rmi = new RideMatchInfo(rm);
					set.add(rmi);
				}
			}
			return set;
		}
		return null;
	}

	/**
	 * Class RideMatch<br>
	 * (Parent Class Matcher)
	 * Represents a match between two Rides, also stores the common user role and preferred match.
	 * @author João Lucas Pires, Sara Ferreira
	 */
	public class RideMatch {
		private long id;
		private Map<RideRole, Ride> rides;	
		private RideRole userRole;
		private PreferredMatch userPreferredMatch;
		
		public RideMatch(Ride left, Ride right) {
			this.rides = new HashMap<RideRole, Ride>();
			this.rides.put(left.getRideRole(), left);
			this.rides.put(right.getRideRole(), right);
			this.userRole = left.getRideRole();
			if (left.getUser() == null)
				this.userPreferredMatch = PreferredMatch.BETTER;
			else
				this.userPreferredMatch = left.getUser().getPreferredMatch();
			this.setId();
		}
		
		public void setId() {
			this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
			return;
		}
		public long getId() {
			return this.id;
		}

		public Ride getRide(RideRole role) {
			return this.rides.get(role);
		}

		private boolean matchable() {
			Ride left = rides.get(userRole);
			Ride right = rides.get(userRole.other());
			if (left == null || right == null)
				return false;
			if (left.isMatched() || right.isMatched() )
				return false;
			if (Trie.getDistance(left.getCurrent().getX(),left.getCurrent().getY(),right.getCurrent().getX(),right.getCurrent().getY()) > getRadius())
				return false;
			if (Trie.getDistance(left.getTo().getX(),left.getTo().getY(),right.getTo().getX(),right.getTo().getY()) > getRadius())
				return false;
			return true;
		}

		public Map<RideRole, Ride> getRides() {
			return this.rides;
		}

		public RideRole getUserRole() {
			return this.userRole;
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
	}

}