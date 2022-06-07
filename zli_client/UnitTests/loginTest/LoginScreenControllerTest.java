package loginTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import home.LoginScreenController;

class LoginScreenControllerTest {
	@Mock
	private LoginScreenController loginScreenControllerMock;
	private Method testIsUserInputValidMethod;

	@BeforeEach
	void setUp() throws Exception {
		testIsUserInputValidMethod =  LoginScreenController.class.getDeclaredMethod("isUserInputValid", String.class, String.class);
		testIsUserInputValidMethod.setAccessible(true);
		loginScreenControllerMock = Mockito.mock(LoginScreenController.class);
	}

	@Test
	void login_isUserInputValid_alreadyLoggedIn_returnsFalse() {
		when(loginScreenControllerMock.isUserLoggedIn()).thenReturn(true);  // logged in
		doNothing().when(loginScreenControllerMock).setError(any(String.class));
		doNothing().when(loginScreenControllerMock).disableLoginButton();  // logged in

		try {
			boolean result = (boolean) testIsUserInputValidMethod.invoke(loginScreenControllerMock, "", "");
			assertFalse(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	void login_isUserInputValid_notLoggedIn_successful() {
		when(loginScreenControllerMock.isUserLoggedIn()).thenReturn(false);  // logged in
		doNothing().when(loginScreenControllerMock).setError(any(String.class));

		try {
			boolean result = (boolean) testIsUserInputValidMethod.invoke(loginScreenControllerMock, "dolav", "yeena");
			assertTrue(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	void login_isUserInputValid_notLoggedIn_fail() {
		when(loginScreenControllerMock.isUserLoggedIn()).thenReturn(false);  // logged in
		doNothing().when(loginScreenControllerMock).setError(any(String.class));

		try {
			boolean result = (boolean) testIsUserInputValidMethod.invoke(loginScreenControllerMock, "", "yeena");
			assertFalse(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	
	@Test
	void login_isUserInputValid_notLoggedIn_fail2() {
		when(loginScreenControllerMock.isUserLoggedIn()).thenReturn(false);  // logged in

		try {
			boolean result = (boolean) testIsUserInputValidMethod.invoke(loginScreenControllerMock, "asdf", "");
			assertFalse(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	void login_isUserInputValid_notLoggedIn_fail3() {
		when(loginScreenControllerMock.isUserLoggedIn()).thenReturn(false);  // logged in

		try {
			boolean result = (boolean) testIsUserInputValidMethod.invoke(loginScreenControllerMock, "", "");
			assertFalse(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}