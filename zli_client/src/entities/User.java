package entities;

import java.io.Serializable;
import java.util.HashMap;

import client.ClientFormController;
import util.Commands;
import util.UserType;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idUser = -1;
	private String username = "guest";
	private int idAccount = -1;
	private UserType userType = UserType.UNDEFINED;
	private boolean isLogged = false;
	private static User userInstance = null;

	private User() {
	}

	public static User getUserInstance() {
		return userInstance == null ? userInstance = new User() : userInstance;
	}

	public void login(int idUser, String username, int idAccount, UserType userType) {
		if (!isLogged) {
			this.idUser = idUser;
			this.username = username;
			this.idAccount = idAccount;
			this.userType = userType;
			isLogged = true;
		}
	}

	public void logout()  {
		if (isLogged) {
			// updateDB
			HashMap<String, Object> message = new HashMap<>();
			message.put("command", Commands.LOGOUT);
			message.put("logoutID", User.getUserInstance().getIdUser());
			if((boolean)ClientFormController.client.accept(message))
			{
				System.out.println("logout");
			}
			else
				System.out.println("failed");
			this.idUser = -1;
			this.username = "guest";
			this.idAccount = -1;
			this.userType = UserType.UNDEFINED;
			isLogged = false;
		}
	}


	public String toString() {
		return "User " + username + " logged\nidUser: " + idUser + "\nidAccount: " + idAccount + "\nuserType: "
				+ userType;
	}

	public boolean isUserLoggedIn() {
		return isLogged;
	}

	public String getUsername() {
		return username;
	}

	public int getIdUser() {
		return idUser;
	}
	public UserType getType()
	{
		return userType;
	}

}
