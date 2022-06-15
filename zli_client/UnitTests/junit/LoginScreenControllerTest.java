package junit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import home.LoginScreenController;
import util.Status;

public class LoginScreenControllerTest {

	@Spy
	private LoginScreenController loginScreenControllerMock = new LoginScreenController();
	private Method testIsUserInputValidMethod;
	private HashMap<String, Object> responseAlreadyLoggedIn = new HashMap<>();
	private HashMap<String, Object> responseNotRegistered = new HashMap<>();
	private HashMap<String, Object> responseSuspended = new HashMap<>();
	private HashMap<String, Object> responseNewLogIn = new HashMap<>();

	@Before
	public void setUp() throws Exception {
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

	// checking functionality:login parameters when user logged in
	// input data: (UserStatus,Username,Password):(User already logged in,"","")
	// expected result: false
	@Test
	public void isUserInputValid_alreadyLoggedIn() {
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

	// checking functionality:login parameters with username and password valid
	// input data:(UserStatus,Username,Password):(Guest(not logged in),"dolev","yeena")
	// expected result: true
	@Test
	public void isUserInputValid_successful() {
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

	// checking functionality:login parameters when username is empty and password is valid
	// input data: (Username,Password): ("","yeena")
	// expected result: false
	@Test
	public void isUserInputValid_emptyUsername() {
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

	// checking functionality:login parameters when password is empty and username is valid
	// input data: (Username,Password):("asdf","")
	// expected result: false
	@Test
	public void isUserInputValid_emptyPassword() {
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

	// checking functionality:login parameters when password and username are empty
	// input data: (Username,Password): ("","")
	// expected result: false
	@Test
	public void isUserInputValid_emptyPasswordAndUsername() {
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

	// checking functionality:login when trying to logged in to an already logged in user
	// input data: (Username,UserStatus):("Dolav",AlreadyLoggedIn)
	// expected result: false
	@Test
	public void responseAction_userAlreadyInUse() {
		doNothing().when(loginScreenControllerMock).setError(any(String.class));

		boolean result = loginScreenControllerMock.responseAction("Dolav", responseAlreadyLoggedIn);
		assertFalse(result);
	}

	// checking functionality:login when trying to log in with not registered user
	// input data: (Username,UserStatus):("Dolav",NotRegistered)
	// expected result: false
	@Test
	public void responseAction_userNotRegistered() {
		doNothing().when(loginScreenControllerMock).setError(any(String.class));

		boolean result = loginScreenControllerMock.responseAction("Dolav", responseNotRegistered);
		assertFalse(result);
	}

	// checking functionality: login when trying to log in with Suspended user
	// input data: (Username,UserStatus):("Dolav",Suspended)
	// expected result: false
	@Test
	public void responseAction_Suspended() {
		doNothing().when(loginScreenControllerMock).setError(any(String.class));

		boolean result = (boolean) loginScreenControllerMock.responseAction("Dolav", responseSuspended);
		assertFalse(result);
	}

	// checking functionality: login from cart screen not as customer
	// input data: (Username,UserStatus,UserType):("Dolav",New Log In,not as customers)
	// expected result: false
	@Test
	public void responseAction_loginFromCart_userIsNotCustomer() {
		doNothing().when(loginScreenControllerMock).changeToLogin();
		LoginScreenController.loginFromCart();
		doReturn(false).when(loginScreenControllerMock).cartFlow(); // not customer
		doNothing().when(loginScreenControllerMock).loginUser(any(String.class));

		boolean result = (boolean) loginScreenControllerMock.responseAction("Dolav", responseNewLogIn);
		assertFalse(result);
	}

	// checking functionality: login from cart screen as customer
	// input data: (Username,UserStatus,UserType):("Dolav",NewLogIn,customer)
	// expected result: true
	@Test
	public void responseAction_loginFromCart_userIsCustomer() {
		doNothing().when(loginScreenControllerMock).changeToLogin();
		LoginScreenController.loginFromCart();
		doReturn(true).when(loginScreenControllerMock).cartFlow(); // is customer
		doNothing().when(loginScreenControllerMock).loginUser(any(String.class));

		boolean result = loginScreenControllerMock.responseAction("Dolav", responseNewLogIn);
		assertTrue(result);
	}

	// checking functionality: login from catalog screen
	// input data: (Username,UserStatus):("Dolav",NewLogIn)
	// expected result: true
	@Test
	public void responseAction_loginFromCatalog() {
		doNothing().when(loginScreenControllerMock).changeToLogin();
		LoginScreenController.loginFromCatalog();
		doNothing().when(loginScreenControllerMock).catalogFlow();
		doNothing().when(loginScreenControllerMock).loginUser(any(String.class));

		boolean result = loginScreenControllerMock.responseAction("Dolav", responseNewLogIn);
		assertTrue(result);
	}

	// checking functionality: login from home screen
	// input data: (Username,UserStatus):("Dolav",NewLogIn)
	// expected result: true
	@Test
	public void responseAction_NewLogInFromHome() {
		LoginScreenController.resetLogin();
		doNothing().when(loginScreenControllerMock).changeToHomeScreen();
		doNothing().when(loginScreenControllerMock).CloseWindow();
		doNothing().when(loginScreenControllerMock).loginUser(any(String.class));

		boolean result = loginScreenControllerMock.responseAction("Dolav", responseNewLogIn);
		assertTrue(result);
	}

	// checking functionality:login from cart screen as customer
	// input data: (UserStatus,UserType):(NewLogIn,Customer)
	// expected result: true
	@Test
	public void cartFlow_loginCustomer() {
		doReturn(false).when(loginScreenControllerMock).isUserNotCustomer();
		doNothing().when(loginScreenControllerMock).changeToGreetingCard();
		doNothing().when(loginScreenControllerMock).CloseWindow();

		boolean result = loginScreenControllerMock.cartFlow();
		assertTrue(result);
	}

	// checking functionality:login from cart screen not as customer
	// input data: (UserStatus,UserType):(NewLogIn,Not Customer)
	// expected result: false
	@Test
	public void cartFlow_loginNotCustomer() {
		doReturn(true).when(loginScreenControllerMock).isUserNotCustomer();
		doNothing().when(loginScreenControllerMock).logout();
		doNothing().when(loginScreenControllerMock).setError(any(String.class));

		boolean result = loginScreenControllerMock.cartFlow();
		assertFalse(result);
	}

}
