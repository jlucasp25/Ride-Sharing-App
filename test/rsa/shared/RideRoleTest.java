package rsa.shared;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests on ride roles
 * 
 */
public class RideRoleTest {

	/**
	 * Check if other in ride role enumeration
	 */
	@Test
	public void testOther() {
		assertEquals(RideRole.PASSENGER,RideRole.DRIVER.other());
		assertEquals(RideRole.DRIVER,RideRole.PASSENGER.other());
	}

}
