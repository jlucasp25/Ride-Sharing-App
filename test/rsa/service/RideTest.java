package rsa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rsa.TestData;
import rsa.service.Matcher.RideMatch;
import rsa.shared.Location;
import rsa.shared.RideRole;

public class RideTest extends TestData {
	Matcher matcher;
	Ride ride;
	User user;
	Location from;
	Location to;
	
	@Before
	public void setUp() throws Exception {
		matcher = new Matcher();
		from    = new Location(X1,Y1);
		to      = new Location(X2,Y2);
		ride    = new Ride(user,from,to,PLATES[0],COSTS[0]);
		user    = new User(NICKS[0],NAMES[0],PASSWORDS[0]);
	}

	@Test
	public void testRide() {
		assertNotNull(ride);
	}

	/**
	 * Check that rides have different IDs 
	 */
	@Test
	public void testGetId() {
		Set<Long> rides = new HashSet<Long>();
		
		for(int i=0; i<MANY_OBJECTS; i++)
			rides.add(new Ride(user,from,to,PLATES[0],COSTS[0]).getId());
		
		assertEquals(MANY_OBJECTS,rides.size());
	}

	/**
	 * Check user's setter and getter
	 */
	@Test
	public void testUser() {
		
		for(int i=0; i< NICKS.length; i++) {
			User someUser = new User(NICKS[i],NAMES[i],PASSWORDS[i]);
			Ride someRide = new Ride(someUser,from,to,PLATES[0],COSTS[0]);
				
			assertEquals(NICKS[i],someRide.getUser().getNick());
			assertEquals(PASSWORDS[i],someRide.getUser().getPassword());
		}
	}

	/**
	 * Check plate's setter and getter
	 */
	@Test
	public void testPlate() {
		assertEquals(PLATES[0],ride.getPlate());
		
		for(String plate: PLATES) {
			ride.setPlate(plate);
			assertEquals(plate,ride.getPlate());
		}
	}

	/**
	 * Check is driver
	 */
	@Test
	public void testIsDriver() {
		assertTrue(ride.isDriver());
		
		ride = new Ride(user,from,to,null,COSTS[0]);
		assertFalse(ride.isDriver());
		
	}

	/**
	 * Check ride role getter
	 */
	@Test
	public void testGetRideRole() {
		assertEquals(RideRole.DRIVER,ride.getRideRole());
		
		ride = new Ride(user,from,to,null,COSTS[0]);
		assertEquals(RideRole.PASSENGER,ride.getRideRole());
	}

	/**
	 * Check is passenger
	 */
	@Test
	public void testIsPassenger() {
		assertFalse(ride.isPassenger());
		
		ride = new Ride(user,from,to,null,COSTS[0]);
		assertTrue(ride.isPassenger());
	}

	/**
	 * Check from getter and setter
	 */
	@Test
	public void testFrom() {
		assertEquals(from,ride.getFrom());
		
		ride.setFrom(new Location(X3,Y3));
		assertEquals(X3,ride.getFrom().getX(),DELTA);
		assertEquals(Y3,ride.getFrom().getY(),DELTA);
	}

	/**
	 * Check to getter and setter
	 */
	@Test
	public void testTo() {
		assertEquals(to,ride.getTo());
		
		ride.setTo(new Location(X3,Y3));
		assertEquals(X3,ride.getTo().getX(),DELTA);
		assertEquals(Y3,ride.getTo().getY(),DELTA);
	}

	/**
	 * Check current getter and setter
	 */
	@Test
	public void testCurrent() {
		assertEquals(from,ride.getCurrent());
		
		ride.setCurrent(new Location(X3,Y3));
		assertEquals(X3,ride.getCurrent().getX(),DELTA);
		assertEquals(Y3,ride.getCurrent().getY(),DELTA);
	}

	/**
	 * Check match
	 */
	@Test
	public void testMatch() {
		Ride other = new Ride(user,from,to,null,COSTS[0]);
		RideMatch someMatch = matcher.new RideMatch(ride,other);
		
		assertNull(ride.getMatch());
		ride.setMatch(someMatch);
		assertEquals(someMatch,ride.getMatch());
	}

	/**
	 * Check is matched
	 */
	@Test
	public void testIsMatched() {
		Ride other = new Ride(user,from,to,null,COSTS[0]);
		assertFalse(ride.isMatched());
		
		ride.setMatch(matcher.new RideMatch(ride,other));
		assertTrue(ride.isMatched());
	}

	/**
	 * Check coordinates from current location
	 */
	@Test
	public void testCoordinates() {
		assertEquals(X1,ride.getX(),DELTA);
		assertEquals(Y1,ride.getY(),DELTA);
		
		ride.setCurrent(new Location(X2,Y2));
		assertEquals(X2,ride.getX(),DELTA);
		assertEquals(Y2,ride.getY(),DELTA);
		
		ride.setCurrent(new Location(X3,Y3));
		assertEquals(X3,ride.getX(),DELTA);
		assertEquals(Y3,ride.getY(),DELTA);
	}


}
