package entities;

import util.UserType;

public class User {

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
			System.out.println(this.username + " logged in");
		}
	}

	public void logout() {
		if (isLogged) {
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
}