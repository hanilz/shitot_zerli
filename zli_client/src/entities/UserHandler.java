package entities;

public class UserHandler {
	private final User user;
	
	public UserHandler(User user) {
		this.user = user;
	}
	
	public boolean isLoggedIn() {
		return user.isUserLoggedIn();
	}
}
