package rsa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rsa.TestData;
import rsa.service.Matcher.RideMatch;
import rsa.shared.Car;
import rsa.shared.Location;
import rsa.shared.RideMatchInfo;
import rsa.shared.RideRole;

public class RideMatchTest extends TestData {
	Matcher matcher;
	RideMatchInfo info;
	Location from;
	Location to;
	User driver;
	User passenger;
	Car  car; 
	Ride driverRide;
	Ride passengerRide;
	RideMatch match;

	@Before
	public void setUp() throws Exception {
		matcher = new Matcher();
		
		from     = new Location(X1,Y1);
		to        = new Location(X2,Y2);
		driver    = new User(NICKS[0],NAMES[0],PASSWORDS[0]);
		passenger = new User(NICKS[1],NAMES[1],PASSWORDS[1]);
		car       = new Car(PLATES[0],MAKES[0],MODELS[0],COLORS[0]);
		
		driver.addCar(car);
		driverRide = new Ride(driver,from,to,PLATES[0],COSTS[0]);
	    passengerRide = new Ride(passenger,from,to,null,COSTS[0]);
		match = matcher.new RideMatch(driverRide,passengerRide);
	}
	
	/**
	 * Check if match was created
	 */
	@Test
	public void testRideMatch() {
		assertNotNull(match);
	}

	
	/**
	 * Check that matches have different IDs 
	 */
	@Test
	public void testGetId() {
		Set<Long> ids = new HashSet<>();
		List<RideMatch> matches = new ArrayList<>();
		
		for(int i=0; i<MANY_OBJECTS; i++) {
			RideMatch match = matcher.new RideMatch(driverRide,passengerRide);
		
			matches.add(match); // make sure it isn't garbage collected
			ids.add(match.getId());
		}
		
		assertEquals(MANY_OBJECTS,ids.size());
	}

	/**
	 * Check if ride getters return the correct rides
	 */
	@Test
	public void testGetRide() {
		assertEquals(driverRide.getId(),match.getRide(RideRole.DRIVER).getId());
		assertEquals(passengerRide.getId(),match.getRide(RideRole.PASSENGER).getId());
	}

}
