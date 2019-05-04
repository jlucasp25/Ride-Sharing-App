package rsa.shared;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import rsa.TestData;

/**
 * Test on locations, used by rides
 * 
 */
public class LocationTest extends TestData {
	Location location1, location2, location3;
	
	@Before
	public void setUp() throws Exception {
		location1 = new Location(X1,Y1);
		location2 = new Location(X2,Y2);
		location3 = new Location(X3,Y3);
	}

	/**
	 * Check X coordinate
	 */
	@Test
	public void testX() {
		assertEquals(X1,location1.getX(),DELTA);
		assertEquals(X2,location2.getX(),DELTA);
		assertEquals(X3,location3.getX(),DELTA);
	}

	/**
	 * Check Y coordinate
	 */
	@Test
	public void testGetY() {
		assertEquals(Y1,location1.getY(),DELTA);
		assertEquals(Y2,location2.getY(),DELTA);
		assertEquals(Y3,location3.getY(),DELTA);
	}
	
	/**
	 * Check equals (should be generated using the IDE)
	 */
	@Test
	public void testEquals() {
		assertEquals(location1,new Location(X1,Y1));
		assertEquals(location2,new Location(X2,Y2));
		assertEquals(location3,new Location(X3,Y3));
	}

}
