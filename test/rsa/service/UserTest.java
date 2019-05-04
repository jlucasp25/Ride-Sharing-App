package rsa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rsa.TestData;
import rsa.shared.Car;
import rsa.shared.PreferredMatch;
import rsa.shared.RideRole;
import rsa.shared.UserStars;


/**
 * Tests on User - a participant on rides, with nick, password, 
 * cars and preferred matches
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class UserTest extends TestData {
	User user;
	
	@BeforeClass
	public static void setUpClass() {
		
	}
	
	@Before
	public void setUp() throws Exception {
		user = new User(NICKS[0], NAMES[0], PASSWORDS[0]);
	}

	/**
	 *  Test if instance was created in fixture
	 */
	@Test
	public void testUser() {
		assertNotNull(new User(NICKS[1], NAMES[1], PASSWORDS[1]));
	}

	/**
	 * Check getter and setter of property nick
	 */
	@Test
	public void testNick() {
	
		for(String nick: NICKS) {
			user = new User(nick, NAMES[0], PASSWORDS[0]);
			assertEquals(nick,user.getNick());
		}
		
	}

	/**
	 * Check getter and setter of property password
	 */
	@Test
	public void testSetGetPassword() {
		for(String nick: NICKS) {
			user.setPassword(nick);
			assertEquals(nick,user.getPassword());
		}
	}

	
	/**
	 * Check User authentication with valid and invalid passwords
	 */
	@Test
	public void testAuthenticate() {
		assertTrue(user.authenticate(PASSWORDS[0]));
		
		assertFalse(user.authenticate(PASSWORDS[1]));
		
		user.setPassword(PASSWORDS[1]);
		
		assertTrue(user.authenticate(PASSWORDS[1]));
	}
	
	/**
	 * Check cars associated with user, including multiple cards,
	 * changing car features and removing cards. 
	 */
	@Test
	public void testCars() {
		assertNull(user.getCar(PLATES[0]));
		
		Car car0 = new Car(PLATES[0],MAKES[0],MODELS[0],COLORS[0]); 
		Car car1 = new Car(PLATES[1],MAKES[1],MODELS[1],COLORS[1]); 
		
		user.addCar(car0);
		
		assertEquals(MAKES[0],user.getCar(PLATES[0]).getMake());
		assertNull(user.getCar(PLATES[1]));
		
		user.addCar(car1);
		
		assertEquals(MAKES[0],user.getCar(PLATES[0]).getMake());
		assertEquals(MAKES[1],user.getCar(PLATES[1]).getMake());
		
		assertEquals(COLORS[0],user.getCar(PLATES[0]).getColor());
		
		car0.setColor(COLORS[3]);
		
		assertEquals(COLORS[3],user.getCar(PLATES[0]).getColor());
		
		user.deleteCar(PLATES[0]);
		
		assertNull(user.getCar(PLATES[0]));
		assertEquals(MAKES[1],user.getCar(PLATES[1]).getMake());
		
		user.deleteCar(PLATES[1]);
		
		assertNull(user.getCar(PLATES[0]));
		assertNull(user.getCar(PLATES[1]));
	}

	/**
	 * Check if star average is well computed when stars are added
	 * for user in the two roles (driver and passenger)
	 */
	@Test
	public void testStars() {
		assertEquals(0,user.getAverage(RideRole.DRIVER),DELTA);
		assertEquals(0,user.getAverage(RideRole.PASSENGER),DELTA);
	
		user.addStars(UserStars.FOUR_STARS, RideRole.DRIVER);
		
		assertEquals(4,user.getAverage(RideRole.DRIVER),DELTA);
		assertEquals(0,user.getAverage(RideRole.PASSENGER),DELTA);

		user.addStars(UserStars.FOUR_STARS, RideRole.DRIVER);
		
		assertEquals(4,user.getAverage(RideRole.DRIVER),DELTA);
		assertEquals(0,user.getAverage(RideRole.PASSENGER),DELTA);
		
		user.addStars(UserStars.FIVE_STARS, RideRole.DRIVER);
		
		assertEquals((4D+4D+5D)/3D,user.getAverage(RideRole.DRIVER),DELTA);
		assertEquals(0,user.getAverage(RideRole.PASSENGER),DELTA);
		
		user.addStars(UserStars.THREE_STARS, RideRole.DRIVER);
		user.addStars(UserStars.FIVE_STARS, RideRole.PASSENGER);
		
		assertEquals((4D+4D+5D+3D)/4D,user.getAverage(RideRole.DRIVER),DELTA);
		assertEquals(5,user.getAverage(RideRole.PASSENGER),DELTA);
	}
	
	/**
	 * Check preferred match setter and getter and if it defaults to BETTER 
	 */
	@Test
	public void testPreferredMatch() {
		assertEquals(PreferredMatch.BETTER,user.getPreferredMatch());
		
		for(PreferredMatch preferred: PreferredMatch.values()) {
			user.setPreferredMatch(preferred);
			assertEquals(preferred,user.getPreferredMatch());
		}
		
		user.setPreferredMatch(null); // check default
		assertEquals(PreferredMatch.BETTER,user.getPreferredMatch());
	}
}
