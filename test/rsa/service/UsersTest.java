package rsa.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rsa.TestData;
import rsa.shared.RideSharingAppException;

/**
 * Tests on Users - a collection of User indexed by nick
 * 
 * @author Jos&eacute; Paulo Leal {@code zp@dcc.fc.up.pt}
 */
public class UsersTest extends TestData {
	static Users users;

	@BeforeClass
	public static void setUpClass() throws RideSharingAppException {
		users = Users.getInstance();
	}

	@Before
	public void setUp() throws Exception {
		users.reset();
	}

	/**
	 * Test if instance was created in fixture
	 */
	@Test
	public void testGetInstance() {
		assertNotNull(users);
	}

	/**
	 * Check user registration with invalid nicks, duplicate nicks, multiple
	 * users
	 * @throws RideSharingAppException 
	 */
	@Test
	public void testRegister() throws RideSharingAppException {
		assertFalse("Invalid nick", users.register(INVALID_NICK, NAMES[0], PASSWORDS[0]));
		assertTrue("Valid nick", users.register(NICKS[0], NAMES[0], PASSWORDS[0]));
		assertFalse("Duplicate nick", users.register(NICKS[0], NAMES[0],PASSWORDS[0]));
		assertTrue("Valid nick", users.register(NICKS[1], NAMES[1], PASSWORDS[1]));
		assertFalse("Duplicate nick", users.register(NICKS[1], NAMES[1], PASSWORDS[0]));
	}

	/**
	 * Check password update, with valid password, old password and wrong
	 * password
	 * @throws RideSharingAppException 
	 */
	@Test
	public void testUpdatePassword() throws RideSharingAppException {
		users.register(NICKS[0], NAMES[1], PASSWORDS[0]);

		assertTrue(users.updatePassword(NICKS[0], PASSWORDS[0], PASSWORDS[1]));
		assertFalse(users.updatePassword(NICKS[0], PASSWORDS[0], PASSWORDS[1]));
		assertTrue(users.updatePassword(NICKS[0], PASSWORDS[1], PASSWORDS[0]));
	}

	/**
	 * Check authentication valid and invalid tokens and multiple users
	 * @throws RideSharingAppException 
	 */
	@Test
	public void testAuthenticate() throws RideSharingAppException {
		users.register(NICKS[0], NAMES[0], PASSWORDS[0]);

		assertTrue(users.authenticate(NICKS[0], PASSWORDS[0]));
		assertFalse(users.authenticate(NICKS[0], PASSWORDS[1]));
		assertFalse(users.authenticate(NICKS[1], PASSWORDS[1]));
		assertFalse(users.authenticate(NICKS[1], PASSWORDS[0]));
	}

	/**
	 * Check obtaining a User by nick, when it is available or not, and with
	 * multiple Users
	 * @throws RideSharingAppException 
	 */
	@Test
	public void testGetUser() throws RideSharingAppException {
		assertNull(users.getUser(NICKS[0]));
		assertNull(users.getUser(NICKS[1]));

		users.register(NICKS[0], NAMES[0], PASSWORDS[0]);

		assertNotNull(users.getUser(NICKS[0]));
		assertNull(users.getUser(NICKS[1]));

		users.register(NICKS[1], NAMES[0], PASSWORDS[1]);

		assertNotNull(users.getUser(NICKS[0]));
		assertNotNull(users.getUser(NICKS[1]));
	}

}
