package loginTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import home.LoginScreenController;
import util.Status;

class LoginScreenControllerTest {
	@Spy
	private LoginScreenController loginScreenControllerMock = new LoginScreenController();
	private Method testIsUserInputValidMethod;
	private HashMap<String, Object> responseAlreadyLoggedIn = new HashMap<>();
	private HashMap<String, Object> responseNotRegistered = new HashMap<>();
	private HashMap<String, Object> responseSuspended = new HashMap<>();
	private HashMap<String, Object> responseNewLogIn = new HashMap<>();

	@BeforeEach
	void setUp() throws Exception {
		testIsUserInputValidMethod = LoginScreenController.class.getDeclaredMethod("isUserInputValid", String.class,
				String.class);
		loginScreenControllerMock = Mockito.spy(loginScreenControllerMock);
		LoginScreenController.setInstance(loginScreenControllerMock);
		testIsUserInputValidMethod.setAccessible(true);
		responseAlreadyLoggedIn.put("response", Status.ALREADY_LOGGED_IN);
		responseNotRegistered.put("response", Status.NOT_REGISTERED);
		responseSuspended.put("response", Status.SUSPENDED);
		responseNewLogIn.put("response", Status.NEW_LOG_IN);
	}

	@Test
	void login_isUserInputValid_alreadyLoggedIn_returnsFalse() {
		doReturn(true).when(loginScreenControllerMock).isUserLoggedIn(); // logged in
		doNothing().when(loginScreenControllerMock).setError(any(String.class));
		doNothing().when(loginScreenControllerMock).disableLoginButton(); // logged in

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
		when(loginScreenControllerMock.isUserLoggedIn()).thenReturn(false); // logged in
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
		when(loginScreenControllerMock.isUserLoggedIn()).thenReturn(false); // logged in
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
		when(loginScreenControllerMock.isUserLoggedIn()).thenReturn(false); // logged in
		doNothing().when(loginScreenControllerMock).setError(any(String.class));

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
		when(loginScreenControllerMock.isUserLoggedIn()).thenReturn(false); // logged in
		doNothing().when(loginScreenControllerMock).setError(any(String.class));

		try {
			boolean result = (boolean) testIsUserInputValidMethod.invoke(loginScreenControllerMock, "", "");
			assertFalse(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void login_responseAction_alreadyLoggedIn_fail() {
		doNothing().when(loginScreenControllerMock).setError(any(String.class));

		boolean result = loginScreenControllerMock.responseAction("Dolav", responseAlreadyLoggedIn);
		assertFalse(result);
	}

	@Test
	void login_responseAction_NotRegistered_fail() {
		doNothing().when(loginScreenControllerMock).setError(any(String.class));

		boolean result = loginScreenControllerMock.responseAction("Dolav", responseNotRegistered);
		assertFalse(result);
	}

	@Test
	void login_responseAction_Suspended_fail() {
		doNothing().when(loginScreenControllerMock).setError(any(String.class));

		boolean result = (boolean) loginScreenControllerMock.responseAction("Dolav", responseSuspended);
		assertFalse(result);
	}

	@Test
	void login_responseAction_NewLogIn_isCartTrue_fail() {
		doNothing().when(loginScreenControllerMock).changeToLogin();
		LoginScreenController.loginFromCart();
		doReturn(false).when(loginScreenControllerMock).cartFlow(); // logged in
		doNothing().when(loginScreenControllerMock).loginUser(any(String.class));
		
		boolean result = (boolean) loginScreenControllerMock.responseAction("Dolav", responseNewLogIn);
		assertFalse(result);
	}

	@Test
	void login_responseAction_NewLogIn_isCartTrue_success() {
		doNothing().when(loginScreenControllerMock).changeToLogin();
		LoginScreenController.loginFromCart();
		doReturn(true).when(loginScreenControllerMock).cartFlow(); // logged in
		doNothing().when(loginScreenControllerMock).loginUser(any(String.class));

		boolean result = loginScreenControllerMock.responseAction("Dolav", responseNewLogIn);
		assertTrue(result);
	}

	@Test
	void login_responseAction_NewLogIn_isCatalogTrue_success() {
		doNothing().when(loginScreenControllerMock).changeToLogin();
		LoginScreenController.loginFromCatalog();
		doNothing().when(loginScreenControllerMock).catalogFlow();
		doNothing().when(loginScreenControllerMock).loginUser(any(String.class));

		boolean result = loginScreenControllerMock.responseAction("Dolav", responseNewLogIn);
		assertTrue(result);
	}

	@Test
	void login_responseAction_NewLogIn_default_success() {
		LoginScreenController.resetLogin();
		doNothing().when(loginScreenControllerMock).changeToHomeScreen();
		doNothing().when(loginScreenControllerMock).CloseWindow();
		doNothing().when(loginScreenControllerMock).loginUser(any(String.class));

		boolean result = loginScreenControllerMock.responseAction("Dolav", responseNewLogIn);
		assertTrue(result);
	}
	
	@Test
	void login_cartFlow_isCustomer_success() {
		doReturn(false).when(loginScreenControllerMock).isUserNotCustomer();
		doNothing().when(loginScreenControllerMock).changeToGreetingCard();
		doNothing().when(loginScreenControllerMock).CloseWindow();
		
		boolean result = loginScreenControllerMock.cartFlow();
		assertTrue(result);
	}
	
	@Test
	void login_cartFlow_isNotCustomer_fail() {
		doReturn(true).when(loginScreenControllerMock).isUserNotCustomer();
		doNothing().when(loginScreenControllerMock).logout();
		doNothing().when(loginScreenControllerMock).setError(any(String.class));
		
		boolean result = loginScreenControllerMock.cartFlow();
		assertFalse(result);
	}
}