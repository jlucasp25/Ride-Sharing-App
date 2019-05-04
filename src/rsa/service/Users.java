package rsa.service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import rsa.shared.RideSharingAppException;

/**
 * <b>Class Users</b>
 * <br>This class provides collection of users. Contains methods for registration, authentication and retrieving players and their names. <br>
 * Nicks acts as keys and cannot be changed. They must be a single word (no white characters) of letters, digits and underscores, starting with a letter
 * Users data is serialized for persistency.
 * @author João Lucas Pires, Sara Ferreira
 */
public class Users extends Object implements Serializable {
		
	private static final long serialVersionUID = 1L;
	private static Users instance ;
	private static Map<String, User> users = new HashMap<String,User>();
	private static String filePath = "serialized.txt";
	private static File myFile;
	
	public static File getPlayersFile() {
		return myFile;
	}
	
	public static void setPlayersFile(File managerFile) throws RideSharingAppException {
		myFile = managerFile;
		if(!myFile.exists())
 			throw new RideSharingAppException("Users File doesn't exist");
	}
	
	private static void SetFile() throws IOException, RideSharingAppException {
		File file = new File(filePath);
		if (!file.exists())
 			file.createNewFile();
 		setPlayersFile(file);
				
	}
	public static Users getInstance() throws RideSharingAppException {
		
		if( instance == null ) instance = new Users();
 		if( users == null ) users = new HashMap<>();
 		if( myFile == null )
			try {
				SetFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		else
			try {
				restore();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
 		return instance;
	}
	
	
	@SuppressWarnings("unchecked")
	private static void restore() throws RideSharingAppException, FileNotFoundException, IOException, ClassNotFoundException {
	
		if( myFile == null  || !myFile.exists())
			throw new RideSharingAppException();
		
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(myFile));
			users= (HashMap<String, User>)in.readObject();
			in.close();
		
	}
	
	void reset() throws IOException, RideSharingAppException {
		users.clear();
		if (myFile != null)
				myFile.delete();
		
		instance = new Users();
		users = new HashMap<>();
		Users.SetFile();
	}
	
	boolean register(String nick, String name, String password) throws RideSharingAppException {
		if (users.containsKey(nick) || nick.contains(" "))
			return false;
		
		File serialFile = getPlayersFile();
		User newUser = new User(nick,name,password);
		users.put(nick, newUser);
		if (serialFile == null)
			return false;
		try {
			FileOutputStream outputFile = new FileOutputStream(serialFile);
			ObjectOutputStream objectOutput = new ObjectOutputStream(outputFile);
			
			objectOutput.writeObject(newUser);			
			
			outputFile.close();
			objectOutput.close();
		}
		catch (IOException e) {
			throw new RideSharingAppException("An error has occurred while serializing!");
		}
		System.out.println("The object has been serialized!");
		return true;
	}
	
	boolean updatePassword(String nick, String oldPassword, String newPassword) throws RideSharingAppException {
		if (!users.containsKey(nick))
			return false;
		
		User currentUser = users.get(nick);
		
		if (!currentUser.getPassword().equals(oldPassword))
			return false; 
		
		currentUser.setPassword(newPassword);
		
		File serialFile = getPlayersFile();
		if (serialFile == null)
			return false;
		try {
		
			FileOutputStream outputFile = new FileOutputStream(serialFile);
			ObjectOutputStream objectOutput = new ObjectOutputStream(outputFile);
			
			objectOutput.writeObject(currentUser);			
			
			outputFile.close();
			objectOutput.close();
		}
		catch (IOException e) {
			throw new RideSharingAppException("An error has occurred while serializing!");
		}
		System.out.println("The object has been serialized!");
		return true;
	}
	
	boolean authenticate(String nick, String password) {
		if (users.containsKey(nick))
			if (users.get(nick).getPassword().equals(password))
				return true;
		return false;
	}
	
	public User getUser(String nick) {
		return users.get(nick);
	}
}