package rsa.service;
import java.io.IOException;
import java.util.Set;

import rsa.shared.Location;
import rsa.shared.PreferredMatch;
import rsa.shared.RideMatchInfo;
import rsa.shared.RideRole;
import rsa.shared.RideSharingAppException;
import rsa.shared.UserStars;

/**
 * <b>Class Manager</b>
 * <br>An instance of this class is responsible for managing the ride sharing service, handling user requests and matching their rides. <br>
 * The methods of this class follows the Singleton design pattern to provide a single instance of this class to the application
 * @author João Lucas Pires, Sara Ferreira
 */
public class Manager {
	
	private static Users allUsers;
	private static Matcher matcher;
	private static Manager instance = null;
	
	public void acceptMatch(long rideId, long matchId) {
		matcher.acceptMatch(rideId, matchId);
	}
	
	/**
	 * Adds new ride to the Manager's matcher.
	 * Also verifies if the authentication is correct, if not throws a RideSharingAppException.
	 * @param nick (String)
	 * @param password (String)
	 * @param from (Location)
	 * @param to (Location)
	 * @param plate (String)
	 * @param cost (float)
	 * @return Newly added Ride ID (long)
	 * @throws RideSharingAppException
	 */
	public long addRide(String nick, String password, Location from, Location to, String plate, float cost) throws RideSharingAppException {
		if (!this.authenticate(nick, password))
			throw new RideSharingAppException("Wrong user or Password!");	
		return matcher.addRide(allUsers.getUser(nick), from, to, plate, cost);
	}
	
	/**
	 * Authenticates the user.
	 * @param nick (String)
	 * @param password (String)
	 * @return boolean representing the success of the authentication.
	 */
	public boolean authenticate(String nick, String password) {
		return allUsers.authenticate(nick, password);
	}
	
	/**
	 * Concludes the Ride, adding the classification to the user.
	 * @param rideId (long)
	 * @param classification (UserStars)
	 */
	public void concludeRide(long rideId, UserStars classification) {
		matcher.concludeRide(rideId, classification);
		return;
	}
	/**
	 * Retrieves the average rating for the pair (user,role) specified.
	 * @param nick (String)
	 * @param role (RideRole)
	 * @return double value containing the average rating requested.
	 * @throws RideSharingAppException
	 */
	public double getAverage(String nick, RideRole role) throws RideSharingAppException {
		return (double) allUsers.getUser(nick).getAverage(role);
	}
	
	/**
	 * Creates a new single instance of a Manager. (Singleton Design Pattern)
	 * @return Manager's instance.
	 * @throws RideSharingAppException
	 * @throws IOException 
	 */
	public static Manager getInstance() throws RideSharingAppException, IOException {
		if (instance == null)
			instance = new Manager();
		if (allUsers == null)
			allUsers = Users.getInstance();
		else
			allUsers.reset();
		if (matcher == null)
			matcher = new Matcher();
		return instance;
	}
	
	public PreferredMatch getPreferredMatch(String nick, String password) throws RideSharingAppException {
		if (!this.authenticate(nick, password))
			throw new RideSharingAppException("Wrong user or Password!");	
		return allUsers.getUser(nick).getPreferredMatch();		
	}
	
	public boolean register(String nick, String name, String password) throws RideSharingAppException {
		return allUsers.register(nick, name, password);
	}
	
	/**
	 * Auxiliar method to reset the Manager's instance to allow testing.
	 * @throws RideSharingAppException 
	 * @throws IOException 
	 */
	public void reset() throws RideSharingAppException, IOException {
		allUsers.reset();
		instance = null;
		matcher = null;
		allUsers = null;
	}
	
	public void setPreferredMatch(String nick, String password, PreferredMatch preferred) throws RideSharingAppException {
		if (!this.authenticate(nick, password))
			throw new RideSharingAppException("Wrong user or Password!");	
		allUsers.getUser(nick).setPreferredMatch(preferred);
	}
	
	public boolean updatePassword(String nick, String oldPassword, String newPassword) throws RideSharingAppException {
		return allUsers.updatePassword(nick, oldPassword, newPassword);
	}
	
	public Set<RideMatchInfo> updateRide(long rideId, Location current) {
		return matcher.updateRide(rideId, current);
	}

	public Users getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(Users allUsers) {
		Manager.allUsers = allUsers;
	}

	public Matcher getMatcher() {
		return matcher;
	}

	public void setMatcher(Matcher matcher) {
		Manager.matcher = matcher;
	}
}