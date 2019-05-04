package rsa.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import rsa.TestData;
import rsa.service.Matcher;
import rsa.service.Matcher.RideMatch;
import rsa.service.Ride;
import rsa.service.User;

/**
 * Tests on RideMatchInfo
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class RideMatchInfoTest extends TestData {
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
		Matcher matcher = new Matcher();
		
		from     = new Location(X1,Y1);
		to        = new Location(X2,Y2);
		driver    = new User(NICKS[0],NAMES[0],PASSWORDS[0]);
		passenger = new User(NICKS[1],NAMES[1],PASSWORDS[1]);
		car       = new Car(PLATES[0],MAKES[0],MODELS[0],COLORS[0]);
		
		driver.addCar(car);
		driverRide = new Ride(driver,from,to,PLATES[0],COSTS[0]);
	    passengerRide = new Ride(passenger,from,to,null,COSTS[0]);
		match = matcher.new RideMatch(driverRide,passengerRide);
				
		info = new RideMatchInfo(match);
	}

	/**
	 * Check if a match info was created
	 */
	@Test
	public void testRideMatchInfo() {
		assertNotNull(info);
	}


	/**
	 * Check that retains match id of original match
	 */
	@Test
	public void testGetId() {
		assertEquals(match.getId(),info.getMatchId());
	}

	/**
	 * Check name getter
	 */
	@Test
	public void testGetName() {
		assertEquals(NAMES[0],info.getName(RideRole.DRIVER));
		assertEquals(NAMES[1],info.getName(RideRole.PASSENGER));
	}

	/**
	 * Check stars getter with default value
	 */
	@Test
	public void testGetStars() {
		assertEquals(0,info.getStars(RideRole.DRIVER),DELTA);
		assertEquals(0,info.getStars(RideRole.PASSENGER),DELTA);
	}

	/**
	 * Check where getter
	 */
	@Test
	public void testGetWhere() {
		assertEquals(from,info.getWhere(RideRole.DRIVER));
		assertEquals(from,info.getWhere(RideRole.PASSENGER));
	}

	/**
	 * Check car getter
	 */
	@Test
	public void testGetCar() {
		assertEquals(PLATES[0],info.getCar().getPlate());
	}

	/**
	 * Check the cost getter
	 */
	@Test
	public void testGetCost() {
		assertEquals(COSTS[0],info.getCost(),DELTA);
	}

}
