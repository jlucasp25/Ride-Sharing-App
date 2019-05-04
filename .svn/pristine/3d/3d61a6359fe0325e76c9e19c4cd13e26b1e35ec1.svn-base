package rsa.service;

import static org.junit.Assert.assertEquals;

import java.util.SortedSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rsa.TestData;
import rsa.shared.Car;
import rsa.shared.Location;
import rsa.shared.PreferredMatch;
import rsa.shared.RideMatchInfo;
import rsa.shared.RideRole;
import rsa.shared.UserStars;

/**
 * Test a Matcher. Check if matcher matches the correct rides.
 * 
 * 
 */
public class MatcherTest extends TestData {
	Matcher matcher;
	Location from;
	Location to;
	Location other;
	
	@BeforeClass
	public static void prepare() {
		Matcher.setTopLeft(new Location(TOP_LEFT_X,TOP_LEFT_Y));
		Matcher.setBottomRight(new Location(BOTTOM_RIGHT_X,BOTTOM_RIGHT_Y));
		
		Matcher.setRadius(RADIUS);
	}
	
	@Before
	public void setUp() throws Exception {
		matcher = new Matcher();
		
		from  = new Location(X1,Y1);
		to    = new Location(X2,Y2);
		other = new Location(X3,Y3);
		
	}
	
	/**
	 * Make a test user from standard test data 
	 * @param i
	 * @return
	 */
	private User getUser(int i, int... js) {
		User user = new  User(NICKS[i],NAMES[i],PASSWORDS[i]);
		
		for(int j: js)
			user.addCar(new Car(PLATES[j],MAKES[j], MODELS[j], COLORS[j]));
		
		return user;
	}
	
	/**
	 * Check if to top left corner location was correctly set
	 */
	@Test
	public void testTopLeft() {
		assertEquals(TOP_LEFT_X,Matcher.getTopLeft().getX(),DELTA);
		assertEquals(TOP_LEFT_Y,Matcher.getTopLeft().getY(),DELTA);
	}

	/**
	 * Check if to bottom right corner location was correctly set
	 */
	@Test
	public void testBottomRight() {
		assertEquals(BOTTOM_RIGHT_X,Matcher.getBottomRight().getX(),DELTA);
		assertEquals(BOTTOM_RIGHT_Y,Matcher.getBottomRight().getY(),DELTA);
	}

	/**
	 * Check if radius was correctly set.
	 */
	@Test
	public void testGetRadius() {
		assertEquals(RADIUS,Matcher.getRadius(),DELTA);
	}

	/**
	 * Check if rides don't match when both are drivers
	 */
	@Test
	public void testRidesDontMatchBothDrivers() {
		long driverRideId   = matcher.addRide(getUser(0), from, to, PLATES[0],COSTS[0]);
		long passgrRideId = matcher.addRide(getUser(1), from, to, PLATES[1],COSTS[0]);
	
		SortedSet<RideMatchInfo> driverMatches = matcher.updateRide(driverRideId, from);
		SortedSet<RideMatchInfo> passgrMatches = matcher.updateRide(passgrRideId, from);

		assertEquals(0,driverMatches.size());
		assertEquals(0,passgrMatches.size());
	}
	
	/**
	 * Check if rides don't match when both are passengers
	 */
	@Test
	public void testRidesDontMatchBothPassengers() {
		long driverRideId   = matcher.addRide(getUser(0), from, to, null,COSTS[0]);
		long passgrRideId = matcher.addRide(getUser(1), from, to, null,COSTS[0]);
	
		SortedSet<RideMatchInfo> driverMatches = matcher.updateRide(driverRideId, from);
		SortedSet<RideMatchInfo> passgrMatches = matcher.updateRide(passgrRideId, from);

		assertEquals(0,driverMatches.size());
		assertEquals(0,passgrMatches.size());
	}

	
	/**
	 * Check if rides don't match when destination is different  
	 */
	@Test
	public void testRidesDontMatchDifferentDestination() {
		long driverRideId   = matcher.addRide(getUser(0), from, to, PLATES[0],COSTS[0]);
		long passgrRideId = matcher.addRide(getUser(1), from, other, null,COSTS[0]);
	
		SortedSet<RideMatchInfo> driverMatches = matcher.updateRide(driverRideId, from);
		SortedSet<RideMatchInfo> passgrMatches = matcher.updateRide(passgrRideId, from);

		assertEquals(0,driverMatches.size());
		assertEquals(0,passgrMatches.size());
	}
	

	/**
	 * Check if rides don't match when current position is different  
	 */
	@Test
	public void testRidesDontMatchWhenInDifferentPositions() {
		long driverRideId   = matcher.addRide(getUser(0), from, to, PLATES[0],COSTS[0]);
		long passgrRideId = matcher.addRide(getUser(1), other, to, null,COSTS[0]);
	
		SortedSet<RideMatchInfo> driverMatches = matcher.updateRide(driverRideId, from);
		SortedSet<RideMatchInfo> passgrMatches = matcher.updateRide(passgrRideId, other);

		assertEquals(0,driverMatches.size());
		assertEquals(0,passgrMatches.size());
	}


	/**
	 * Simple match: both rides with same path (origin and destination)
	 * One is driver and other passenger.
	 */
	@Test
	public void testSimpleMatch() {
		User driver = getUser(0);
		User passgr = getUser(1);
		
		long driverRideId = matcher.addRide(driver, from, to, PLATES[0],COSTS[0]);
		long passgrRideId = matcher.addRide(passgr, from, to, null,COSTS[0]);
		
		SortedSet<RideMatchInfo> driverMatches = matcher.updateRide(driverRideId, from);
		SortedSet<RideMatchInfo> passgrMatches = matcher.updateRide(passgrRideId, from);

		assertEquals(1,driverMatches.size());
		assertEquals(1,passgrMatches.size());
		
		RideMatchInfo driverMatch = driverMatches.first();
		RideMatchInfo passgrMatch = passgrMatches.first();
		
		assertEquals(NAMES[0],driverMatch.getName(RideRole.DRIVER));
		assertEquals(NAMES[0],passgrMatch.getName(RideRole.DRIVER));
		
		assertEquals(NAMES[1],driverMatch.getName(RideRole.PASSENGER));
		assertEquals(NAMES[1],passgrMatch.getName(RideRole.PASSENGER));
		
		matcher.acceptMatch(driverRideId, passgrMatch.getMatchId());
		matcher.acceptMatch(passgrRideId, driverMatch.getMatchId());
		
		assertEquals(0,driver.getAverage(RideRole.DRIVER),DELTA);
		assertEquals(0,passgr.getAverage(RideRole.PASSENGER),DELTA);
		
		matcher.concludeRide(driverRideId, UserStars.FOUR_STARS);
		matcher.concludeRide(passgrRideId, UserStars.FIVE_STARS);
		
		assertEquals(5,driver.getAverage(RideRole.DRIVER),DELTA);
		assertEquals(4,passgr.getAverage(RideRole.PASSENGER),DELTA);

	}

	/**
	 * Double match: two drivers with same path (origin and destination)
	 * First has more starts and is used the default preference (BETTER)
	 */
	@Test
	public void testDoubleDriverMatchDefault1() {
		User driver = getUser(0,0);
		User passgr = getUser(1);
		User other  = getUser(2,2);
		
		long driverRideId = matcher.addRide(driver, from, to, PLATES[0],COSTS[0]);
		long passgrRideId = matcher.addRide(passgr, from, to, null,COSTS[0]);
		long otherRideId  = matcher.addRide(other, from, to, PLATES[2],COSTS[0]);
		
		driver.addStars(UserStars.FIVE_STARS, RideRole.DRIVER);
		other.addStars(UserStars.FOUR_STARS, RideRole.DRIVER);
		
		SortedSet<RideMatchInfo> driverMatches = matcher.updateRide(driverRideId, from);
		SortedSet<RideMatchInfo> otherMatches  = matcher.updateRide(otherRideId, from);
		SortedSet<RideMatchInfo> passgrMatches = matcher.updateRide(passgrRideId, from);

		assertEquals(1,driverMatches.size());
		assertEquals(2,passgrMatches.size());
		assertEquals(1,otherMatches.size());
		
		RideMatchInfo driverMatch = driverMatches.first();
		RideMatchInfo passgrMatch = passgrMatches.first();
		
		assertEquals(NAMES[0],driverMatch.getName(RideRole.DRIVER));
		assertEquals(NAMES[0],passgrMatch.getName(RideRole.DRIVER));
		
		assertEquals(PLATES[0],driverMatch.getCar().getPlate());
		assertEquals(PLATES[0],passgrMatch.getCar().getPlate());
	}
	
	/**
	 * Double match: two drivers with same path (origin and destination)
	 * Second has more stars and is used the default preference (BETTER)
	 */
	@Test
	public void testDoubleDriverMatchDefault2() {
		User driver = getUser(0,0);
		User passgr = getUser(1);
		User other  = getUser(2,1,2);
		
		long driverRideId = matcher.addRide(driver, from, to, PLATES[0],COSTS[0]);
		long passgrRideId = matcher.addRide(passgr, from, to, null,COSTS[0]);
		long otherRideId  = matcher.addRide(other, from, to, PLATES[2],COSTS[0]);
		
		driver.addStars(UserStars.THREE_STARS, RideRole.DRIVER);
		other.addStars(UserStars.FOUR_STARS, RideRole.DRIVER);
		
		SortedSet<RideMatchInfo> driverMatches = matcher.updateRide(driverRideId, from);
		SortedSet<RideMatchInfo> otherMatches  = matcher.updateRide(otherRideId, from);
		SortedSet<RideMatchInfo> passgrMatches = matcher.updateRide(passgrRideId, from);
		
		assertEquals(1,driverMatches.size());
		assertEquals(2,passgrMatches.size());
		assertEquals(1,otherMatches.size());
		
		RideMatchInfo passgrMatch = passgrMatches.first();
		
		assertEquals(NAMES[2],passgrMatch.getName(RideRole.DRIVER));
		
		assertEquals(PLATES[2],passgrMatch.getCar().getPlate());
	}
	
	/**
	 * Double match: two drivers with same path (origin and destination)
	 * First has more starts and is used the better driver preference (BETTER)
	 */
	@Test
	public void testDoubleDriverMatchBetter1() {
		User driver = getUser(0,0);
		User passgr = getUser(1);
		User other  = getUser(2,2);
		
		long driverRideId = matcher.addRide(driver, from, to, PLATES[0],COSTS[0]);
		long passgrRideId = matcher.addRide(passgr, from, to, null,COSTS[0]);
		long otherRideId  = matcher.addRide(other, from, to, PLATES[2],COSTS[0]);
		
		passgr.setPreferredMatch(PreferredMatch.BETTER);
		
		driver.addStars(UserStars.FIVE_STARS, RideRole.DRIVER);
		other.addStars(UserStars.FOUR_STARS, RideRole.DRIVER);
		
		SortedSet<RideMatchInfo> driverMatches = matcher.updateRide(driverRideId, from);
		SortedSet<RideMatchInfo> otherMatches  = matcher.updateRide(otherRideId, from);
		SortedSet<RideMatchInfo> passgrMatches = matcher.updateRide(passgrRideId, from);

		assertEquals(1,driverMatches.size());
		assertEquals(2,passgrMatches.size());
		assertEquals(1,otherMatches.size());
		
		RideMatchInfo driverMatch = driverMatches.first();
		RideMatchInfo passgrMatch = passgrMatches.first();
		
		assertEquals(NAMES[0],driverMatch.getName(RideRole.DRIVER));
		assertEquals(NAMES[0],passgrMatch.getName(RideRole.DRIVER));
		
		assertEquals(PLATES[0],driverMatch.getCar().getPlate());
		assertEquals(PLATES[0],passgrMatch.getCar().getPlate());
	}
	
	/**
	 * Double match: two drivers with same path (origin and destination)
	 * Second has more stars and is used the better driver preference (BETTER)
	 */
	@Test
	public void testDoubleDriverMatchBetter2() {
		User driver = getUser(0,0);
		User passgr = getUser(1);
		User other  = getUser(2,1,2);
		
		long driverRideId = matcher.addRide(driver, from, to, PLATES[0],COSTS[0]);
		long passgrRideId = matcher.addRide(passgr, from, to, null,COSTS[0]);
		long otherRideId  = matcher.addRide(other, from, to, PLATES[2],COSTS[0]);
		
		passgr.setPreferredMatch(PreferredMatch.BETTER);
		
		driver.addStars(UserStars.THREE_STARS, RideRole.DRIVER);
		other.addStars(UserStars.FOUR_STARS, RideRole.DRIVER);
		
		SortedSet<RideMatchInfo> driverMatches = matcher.updateRide(driverRideId, from);
		SortedSet<RideMatchInfo> otherMatches  = matcher.updateRide(otherRideId, from);
		SortedSet<RideMatchInfo> passgrMatches = matcher.updateRide(passgrRideId, from);
		
		assertEquals(1,driverMatches.size());
		assertEquals(2,passgrMatches.size());
		assertEquals(1,otherMatches.size());
		
		RideMatchInfo passgrMatch = passgrMatches.first();
		
		assertEquals(NAMES[2],passgrMatch.getName(RideRole.DRIVER));
		
		assertEquals(PLATES[2],passgrMatch.getCar().getPlate());
	}
	
	
	/**
	 * Double match: two drivers with same path (origin and destination)
	 * First has more starts and is used the cheapest ride preference (CHEAPER)
	 */
	@Test
	public void testDoubleDriverMatchCheaper1() {
		User driver = getUser(0,0);
		User passgr = getUser(1);
		User other  = getUser(2,2);
		
		long driverRideId = matcher.addRide(driver, from, to, PLATES[0],COSTS[1]);
		long passgrRideId = matcher.addRide(passgr, from, to, null,COSTS[0]);
		long otherRideId  = matcher.addRide(other, from, to, PLATES[2],COSTS[2]);
		
		passgr.setPreferredMatch(PreferredMatch.CHEAPER);
		
		driver.addStars(UserStars.FIVE_STARS, RideRole.DRIVER);
		other.addStars(UserStars.FOUR_STARS, RideRole.DRIVER);
		
		SortedSet<RideMatchInfo> driverMatches = matcher.updateRide(driverRideId, from);
		SortedSet<RideMatchInfo> otherMatches  = matcher.updateRide(otherRideId, from);
		SortedSet<RideMatchInfo> passgrMatches = matcher.updateRide(passgrRideId, from);

		assertEquals(1,driverMatches.size());
		assertEquals(2,passgrMatches.size());
		assertEquals(1,otherMatches.size());
		
		RideMatchInfo driverMatch = driverMatches.first();
		RideMatchInfo passgrMatch = passgrMatches.first();
		
		assertEquals(NAMES[0],driverMatch.getName(RideRole.DRIVER));
		assertEquals(NAMES[0],passgrMatch.getName(RideRole.DRIVER));
		
		assertEquals(PLATES[0],driverMatch.getCar().getPlate());
		assertEquals(PLATES[0],passgrMatch.getCar().getPlate());
	}
	
	/**
	 * Double match: two drivers with same path (origin and destination)
	 * Second has more stars and is used the cheapest ride preference (CHEAPER)
	 */
	@Test
	public void testDoubleDriverMatchCheaper2() {
		User driver = getUser(0,0);
		User passgr = getUser(1);
		User other  = getUser(2,1,2);
		
		long driverRideId = matcher.addRide(driver, from, to, PLATES[0],COSTS[2]);
		long passgrRideId = matcher.addRide(passgr, from, to, null,COSTS[0]);
		long otherRideId  = matcher.addRide(other, from, to, PLATES[2],COSTS[1]);
		
		passgr.setPreferredMatch(PreferredMatch.CHEAPER);
		
		driver.addStars(UserStars.THREE_STARS, RideRole.DRIVER);
		other.addStars(UserStars.FOUR_STARS, RideRole.DRIVER);
		
		SortedSet<RideMatchInfo> driverMatches = matcher.updateRide(driverRideId, from);
		SortedSet<RideMatchInfo> otherMatches  = matcher.updateRide(otherRideId, from);
		SortedSet<RideMatchInfo> passgrMatches = matcher.updateRide(passgrRideId, from);
		
		assertEquals(1,driverMatches.size());
		assertEquals(2,passgrMatches.size());
		assertEquals(1,otherMatches.size());
		
		RideMatchInfo passgrMatch = passgrMatches.first();
		
		assertEquals(NAMES[2],passgrMatch.getName(RideRole.DRIVER));
		
		assertEquals(PLATES[2],passgrMatch.getCar().getPlate());
	}
	
	/**
	 * Double match: two drivers with same path (origin and destination)
	 * First has more starts and is used the closer ride preference (CLOSER)
	 */
	@Test
	public void testDoubleDriverMatchCloser1() {
		User driver = getUser(0,0);
		User passgr = getUser(1);
		User other  = getUser(2,2);
		
		Location near = new Location(X1+RADIUS,Y1);
		
		long driverRideId = matcher.addRide(driver, from, to, PLATES[0],COSTS[1]);
		long passgrRideId = matcher.addRide(passgr, from, to, null,COSTS[0]);
		long otherRideId  = matcher.addRide(other, from, to, PLATES[2],COSTS[2]);
		
		passgr.setPreferredMatch(PreferredMatch.CLOSER);
		
		driver.addStars(UserStars.FIVE_STARS, RideRole.DRIVER);
		other.addStars(UserStars.FOUR_STARS, RideRole.DRIVER);
		
		SortedSet<RideMatchInfo> driverMatches = matcher.updateRide(driverRideId, from);
		SortedSet<RideMatchInfo> otherMatches  = matcher.updateRide(otherRideId, near);
		SortedSet<RideMatchInfo> passgrMatches = matcher.updateRide(passgrRideId, from);

		assertEquals(1,driverMatches.size());
		assertEquals(2,passgrMatches.size());
		assertEquals(1,otherMatches.size());
		
		RideMatchInfo driverMatch = driverMatches.first();
		RideMatchInfo passgrMatch = passgrMatches.first();
		
		assertEquals(NAMES[0],driverMatch.getName(RideRole.DRIVER));
		assertEquals(NAMES[0],passgrMatch.getName(RideRole.DRIVER));
		
		assertEquals(PLATES[0],driverMatch.getCar().getPlate());
		assertEquals(PLATES[0],passgrMatch.getCar().getPlate());
	}
	
	/**
	 * Double match: two drivers with same path (origin and destination)
	 * Second has more stars and is used the closer ride preference (CLOSER)
	 */
	@Test
	public void testDoubleDriverMatchCloser2() {
		User driver = getUser(0,0);
		User passgr = getUser(1);
		User other  = getUser(2,1,2);
		
		Location near = new Location(X1+RADIUS,Y1);
		
		long driverRideId = matcher.addRide(driver, from, to, PLATES[0],COSTS[2]);
		long passgrRideId = matcher.addRide(passgr, from, to, null,COSTS[0]);
		long otherRideId  = matcher.addRide(other, from, to, PLATES[2],COSTS[1]);
		
		passgr.setPreferredMatch(PreferredMatch.CLOSER);
		
		driver.addStars(UserStars.THREE_STARS, RideRole.DRIVER);
		other.addStars(UserStars.FOUR_STARS, RideRole.DRIVER);
		
		SortedSet<RideMatchInfo> driverMatches = matcher.updateRide(driverRideId, near);
		SortedSet<RideMatchInfo> otherMatches  = matcher.updateRide(otherRideId, from);
		SortedSet<RideMatchInfo> passgrMatches = matcher.updateRide(passgrRideId, from);
		
		assertEquals(1,driverMatches.size());
		assertEquals(2,passgrMatches.size());
		assertEquals(1,otherMatches.size());
		
		RideMatchInfo passgrMatch = passgrMatches.first();
		
		assertEquals(NAMES[2],passgrMatch.getName(RideRole.DRIVER));
		
		assertEquals(PLATES[2],passgrMatch.getCar().getPlate());
	}
}
