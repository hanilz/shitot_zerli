package loginTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import entities.User;
import entities.UserHandler;
import home.LoginScreenController;

class LoginScreenControllerTest {
	@Mock
	private User userMock;
	private LoginScreenController loginScreenController;
	private UserHandler userHandler;
	//private User defaultUser;

	@BeforeEach
	void setUp() throws Exception {
		userMock = Mockito.mock(User.class);
		loginScreenController= new LoginScreenController();
		userHandler = new UserHandler(userMock);
		//when(User.getUserInstance()).thenReturn(userMock);
	//	defaultUser=User.getUserInstance();
	}

	@Test
	void isLoggedIn_notLoggedIn() {
		when(userMock.isUserLoggedIn()).thenReturn(false);
		boolean result=userHandler.isLoggedIn();
		assertFalse(result);
	}

	@Test
	void isLoggedIn_LoggedIn() {
		when(userMock.isUserLoggedIn()).thenReturn(true);
		boolean result=userHandler.isLoggedIn();
		assertTrue(result);
	}
}
