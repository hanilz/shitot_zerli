package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import client.ClientFormController;
import util.Commands;
import util.ManageClients;
import util.Screens;
import util.UserType;

/**
 * Logged in User(singleton) in the current system
 */
public class User implements Serializable {

	/**
	 * Allowing serial to send to the server
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * User id of user Default id=-1
	 */
	private int idUser = -1;
	/**
	 * User name of user default=guest
	 */
	private String username = "guest";
	/**
	 * User password
	 */
	private String password;
	/**
	 * User account id account Default account id=-1
	 */
	private int idAccount = -1;
	/**
	 * User type Default=UNDEFINED(Guest);
	 */
	private UserType userType = UserType.UNDEFINED;

	/**
	 * User home screens for setting up the custom home for user
	 * default=(exit,login,catalog)
	 */
	private ArrayList<Screens> userHomeScreens = new ArrayList<>();

	/**
	 * User login status
	 */
	private boolean isLogged = false;

	/**
	 * User Zer-li app cash default=0
	 */
	private double storeCredit = 0;
	/**
	 * User instance(Singleton)
	 */
	private static User userInstance = null;

	/**
	 * Constructor for singleton
	 */
	private User() {
	}

	/**
	 * @return User in system
	 */
	public static User getUserInstance() {
		return userInstance == null ? userInstance = new User() : userInstance;
	}


	/**
	 * Setting up the user in the client system
	 */
	public void login(int idUser, String username, int idAccount, UserType userType, double storeCredit) {
		this.idUser = idUser;
		this.username = username;
		this.idAccount = idAccount;
		this.userType = userType;
		this.storeCredit = storeCredit;
		isLogged = true;
	}

	/**
	 * Logout the user from server by updating user status in DB and logout from client system
	 */
	public void logout() {
		if (isLogged) {
			HashMap<String, Object> message = new HashMap<>();
			message.put("command", Commands.LOGOUT);
			message.put("logoutID", User.getUserInstance().getIdUser());
			if ((boolean) ClientFormController.client.accept(message))
				System.out.println("logout");
			else
				System.out.println("failed");
			this.idUser = -1;
			this.username = "guest";
			this.idAccount = -1;
			this.userType = UserType.UNDEFINED;
			clearUserHomeScreens();
			isLogged = false;
		}
	}

	/**
	 * Creating local user for server in order to set structure of users
	 */
	public User(String username, String password, UserType userType, double storeCredit) {
		this.username = username;
		this.password = password;
		;
		this.userType = userType;
		isLogged = false;
		this.storeCredit = storeCredit;
	}

	/**
	 * @return user home screens buttons
	 */
	public ArrayList<Screens> getUserHomeScreens() {
		return userHomeScreens;
	}

	/**
	 * Deleting all current home screens in local user only
	 */
	public void clearUserHomeScreens() {
		userHomeScreens.clear();
	}

	/**
	 * @return default user home screen buttons (defined in ManageClients)
	 */
	public ArrayList<Screens> getDefaultScreens() {
		return ManageClients.getUserScreens(userType);
	}

	/**
	 * @param userHomeScreens
	 * Setting local user to have a give home screens buttons
	 */
	public void setUserScreens(ArrayList<Screens> userHomeScreens) {
		this.userHomeScreens = userHomeScreens;
	}

	public String toString() {
		return "User " + username + " logged\nidUser: " + idUser + "\nidAccount: " + idAccount + "\nuserType: "
				+ userType;
	}

	/**
	 * @return user logging status
	 */
	public boolean isUserLoggedIn() {
		return isLogged;
	}

	/**
	 * @return User name
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return user id
	 */
	public int getIdUser() {
		return idUser;
	}

	/**
	 * @return User type
	 */
	public UserType getType() {
		return userType;
	}

	/**
	 * @param type
	 * setting local user type 
	 */
	public void setType(String type) {
		userType = UserType.get(type);
	}

	/**
	 *Comparing two users by user id
	 */
	@Override
	public boolean equals(Object user) {
		User currUser = (User) user;
		return currUser.idUser == idUser;
	}

	/**
	 * @return user store credit
	 */
	public double getStoreCredit() {
		return storeCredit;
	}

	/**
	 * @return user password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 * Setting local user password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param storeCredit
	 * Setting local user store credit
	 */
	public void setStoreCredit(double storeCredit) {
		this.storeCredit = storeCredit;
	}
}
