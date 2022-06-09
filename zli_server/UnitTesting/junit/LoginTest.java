package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import util.AnalayzeCommand;
import util.DataBaseController;
import util.Status;
import util.UserType;

class LoginTest {
	// username and passwords for logins
	private String userNotRegistered;
	private String userAlreadyLoggedIn;
	private String userSuspended;
	private String userNewLogInCustomer;
	private String userNewLogInNewCustomer;
	private String userNewLogInStoreManger;
	private String userNewLogInCustomerService;
	private String userNewLogInMarketingWorker;
	private String userNewLogInServiceExpert;
	private String userNewLogInCEO;
	private String userNewLogInDeliveryCoordinator;
	private String userNewLogInStoreWorker;
	
	// userIds for switching isLogin from 1 to 0 after all tests are done
	private static Integer userNewLogInCustomerId;
	private static Integer userNewLogInNewCustomerId;
	private static Integer userNewLogInStoreMangerId;
	private static Integer userNewLogInCustomerServiceId;
	private static Integer userNewLogInMarketingWorkerId;
	private static Integer userNewLogInServiceExpertId;
	private static Integer userNewLogInCEOId;
	private static Integer userNewLogInDeliveryCoordinatorId;
	private static Integer userNewLogInStoreWorkerId;
	
	// all expected responses from AnalyzeCommand to assert later
	private HashMap<String, Object> expectedResponseAlreadyLoggedIn = new HashMap<>();
	private HashMap<String, Object> expectedResponseNotRegistered = new HashMap<>();
	private HashMap<String, Object> expectedResponseSuspended = new HashMap<>();
	private HashMap<String, Object> expectedResponseNewLogInCustomer = new HashMap<>();
	private HashMap<String, Object> expectedResponseNewLogInNewCustomer = new HashMap<>();
	private HashMap<String, Object> expectedResponseNewLogInStoreManger = new HashMap<>();
	private HashMap<String, Object> expectedResponseNewLogInCustomerService = new HashMap<>();
	private HashMap<String, Object> expectedResponseNewLogInMarketingWorker = new HashMap<>();
	private HashMap<String, Object> expectedResponseNewLogInServiceExpert = new HashMap<>();
	private HashMap<String, Object> expectedResponseNewLogInCEO = new HashMap<>();
	private HashMap<String, Object> expectedResponseNewLogInDeliveryCoordinator = new HashMap<>();
	private HashMap<String, Object> expectedResponseNewLogInStoreWorker = new HashMap<>();

	@BeforeEach
	void setUp() throws Exception  {
		DataBaseController.defultConnect();  // set up conn for tests
		
		setUpLogIns();
		setUpNewLogInIds();
		setUpExpectedResponses();
	}

	// checking functionality: user's isLogin column is 1 - returned status is ALREADY_LOGGED_IN
	// input data: (username, password):("yana", "yana")
	// expected result: HashMap<String, Object> = 	{"response": Status.ALREADY_LOGGED_IN}
	@Test
	public void login_alreadyLoggedIn_returnStatusAlreadyLoggedIn() {
		HashMap<String, Object> returnedResponse = AnalayzeCommand.loginUser(userAlreadyLoggedIn, userAlreadyLoggedIn);
		assertEquals(returnedResponse, expectedResponseAlreadyLoggedIn);
	}

	// checking functionality: user doesn't exist - returned status is NOT_REGISTERED
	// input data: (username, password):("shusty", "shusty")
	// expected result: HashMap<String, Object> = 	{"response": Status.NOT_REGISTERED}
	@Test
	public void login_notRegistered_returnStatusNotRegistered() {
		HashMap<String, Object> returnedResponse = AnalayzeCommand.loginUser(userNotRegistered, userNotRegistered);
		assertEquals(returnedResponse, expectedResponseNotRegistered);
	}
	
	// checking functionality: user's status column is "Suspended" - returned status is SUSPENDED
	// input data: (username, password):("hanil", "hanil")
	// expected result: HashMap<String, Object> = 	{"response": Status.SUSPENDED}
	@Test
	public void login_suspended_returnStatusSuspended() {
		HashMap<String, Object> returnedResponse = AnalayzeCommand.loginUser(userSuspended, userSuspended);
		assertEquals(returnedResponse, expectedResponseSuspended);
	}
	
	// checking functionality: NEW_LOG_IN user with userType CUSTOMER
	// input data: (username, password):("dolev", "dolev")
	// expected result: HashMap<String, Object> = 	{"response": Status.NEW_LOG_IN, 
	//												 "idUser": 6, 
	//												 "idAccount": 6, 
	// 												 "userType": UserType.CUSTOMER, 
	//												 "StoreCredit": 0.0
	//												 }
	@Test
	public void login_newLogIn_returnStatusNewLogInUserTypeCustomer() {
		HashMap<String, Object> returnedResponse = AnalayzeCommand.loginUser(userNewLogInCustomer, userNewLogInCustomer);
		assertEquals(returnedResponse, expectedResponseNewLogInCustomer);
	}
	
	// checking functionality: NEW_LOG_IN user with userType NEW_CUSTOMER
	// input data: (username, password):("yael", "yael")
	// expected result: HashMap<String, Object> = 	{"response": Status.NEW_LOG_IN, 
	//												 "idUser": 5, 
	//												 "idAccount": 5, 
	// 												 "userType": UserType.NEW_CUSTOMER, 
	//												 "StoreCredit": 0.0
	//												 }
	@Test
	public void login_newLogIn_returnStatusNewLogInUserTypeNewCustomer() {
		HashMap<String, Object> returnedResponse = AnalayzeCommand.loginUser(userNewLogInNewCustomer, userNewLogInNewCustomer);
		assertEquals(returnedResponse, expectedResponseNewLogInNewCustomer);
	}
	
	// checking functionality: NEW_LOG_IN user with userType STORE_MANGER
	// input data: (username, password):("mng", "mng")
	// expected result: HashMap<String, Object> = 	{"response": Status.NEW_LOG_IN, 
	// 												 "idUser": 7, 
	//												 "idAccount": 7, 
	// 												 "userType": UserType.STORE_MANGER, 
	//												 "StoreCredit": 0.0
	//												 }
	@Test
	public void login_newLogIn_returnStatusNewLogInUserTypeStoreManger() {
		HashMap<String, Object> returnedResponse = AnalayzeCommand.loginUser(userNewLogInStoreManger, userNewLogInStoreManger);
		assertEquals(returnedResponse, expectedResponseNewLogInStoreManger);
	}
	
	// checking functionality: NEW_LOG_IN user with userType CUSTOMER_SERVICE
	// input data: (username, password):("cs", "cs")
	// expected result: HashMap<String, Object> = 	{"response": Status.NEW_LOG_IN, 
	// 												 "idUser": 8, 
	//												 "idAccount": 8, 
	// 												 "userType": UserType.CUSTOMER_SERVICE, 
	//												 "StoreCredit": 0.0
	//												 }
	@Test
	public void login_newLogIn_returnStatusNewLogInUserTypeCustomerService() {
		HashMap<String, Object> returnedResponse = AnalayzeCommand.loginUser(userNewLogInCustomerService, userNewLogInCustomerService);
		assertEquals(returnedResponse, expectedResponseNewLogInCustomerService);
	}
	
	// checking functionality: NEW_LOG_IN user with userType MARKETING_WORKER
	// input data: (username, password):("mrkt", "mrkt")
	// expected result: HashMap<String, Object> = 	{"response": Status.NEW_LOG_IN, 
	// 												 "idUser": 9, 
	//												 "idAccount": 9, 
	// 												 "userType": UserType.MARKETING_WORKER, 
	//												 "StoreCredit": 0.0
	//												 }
	@Test
	public void login_newLogIn_returnStatusNewLogInUserTypeMarketingWorker() {
		HashMap<String, Object> returnedResponse = AnalayzeCommand.loginUser(userNewLogInMarketingWorker, userNewLogInMarketingWorker);
		assertEquals(returnedResponse, expectedResponseNewLogInMarketingWorker);
	}
	
	// checking functionality: NEW_LOG_IN user with userType SERVICE_EXPERT
	// input data: (username, password):("se", "se")
	// expected result: HashMap<String, Object> = 	{"response": Status.NEW_LOG_IN, 
	// 												 "idUser": 16, 
	//												 "idAccount": 16, 
	// 												 "userType": UserType.SERVICE_EXPERT, 
	//												 "StoreCredit": 0.0
	//												 }
	@Test
	public void login_newLogIn_returnStatusNewLogInUserTypeServiceExpert() {
		HashMap<String, Object> returnedResponse = AnalayzeCommand.loginUser(userNewLogInServiceExpert, userNewLogInServiceExpert);
		assertEquals(returnedResponse, expectedResponseNewLogInServiceExpert);
	}
	
	// checking functionality: NEW_LOG_IN user with userType CEO
	// input data: (username, password):("ceo", "ceo")
	// expected result: HashMap<String, Object> = 	{"response": Status.NEW_LOG_IN, 
	// 												 "idUser": 17, 
	//												 "idAccount": 17, 
	// 												 "userType": UserType.CEO, 
	//												 "StoreCredit": 0.0
	//												 }
	@Test
	public void login_newLogIn_returnStatusNewLogInUserTypeCEO() {
		HashMap<String, Object> returnedResponse = AnalayzeCommand.loginUser(userNewLogInCEO, userNewLogInCEO);
		assertEquals(returnedResponse, expectedResponseNewLogInCEO);
	}
	
	// checking functionality: NEW_LOG_IN user with userType DELIVERY_COORDINATOR
	// input data: (username, password):("dc", "dc")
	// expected result: HashMap<String, Object> = 	{"response": Status.NEW_LOG_IN, 
	// 												 "idUser": 18, 
	//												 "idAccount": 20, 
	// 												 "userType": UserType.DELIVERY_COORDINATOR, 
	//												 "StoreCredit": 0.0
	//												 }
	@Test
	public void login_newLogIn_returnStatusNewLogInUserTypeDeliveryCoordinator() {
		HashMap<String, Object> returnedResponse = AnalayzeCommand.loginUser(userNewLogInDeliveryCoordinator, userNewLogInDeliveryCoordinator);
		assertEquals(returnedResponse, expectedResponseNewLogInDeliveryCoordinator);
	}
	
	// checking functionality: NEW_LOG_IN user with userType STORE_WORKER
	// input data: (username, password):("sw", "sw")
	// expected result: HashMap<String, Object> = 	{"response": Status.NEW_LOG_IN, 
	// 												 "idUser": 19, 
	//												 "idAccount": 21, 
	// 												 "userType": UserType.STORE_WORKER, 
	//												 "StoreCredit": 0.0
	//												 }
	@Test
	public void login_newLogIn_returnStatusNewLogInUserTypeStoreWorker() {
		HashMap<String, Object> returnedResponse = AnalayzeCommand.loginUser(userNewLogInStoreWorker, userNewLogInStoreWorker);
		assertEquals(returnedResponse, expectedResponseNewLogInStoreWorker);
	}
	// checking functionality: username is null and password is null - throws NullPointerException
	// input data: (username, password):(null, null)
	// expected result: NullPointerException
	@Test
	public void login_nullUsernameNullPassword_catchNullPointerException() {
		try {
			AnalayzeCommand.loginUser(null, null);
			fail();
		} catch (NullPointerException e) {}
	}
	
	// checking functionality: username is null - throws NullPointerException
	// input data: (username, password):(null, "check null")
	// expected result: NullPointerException
	@Test
	public void login_nullUsername_catchNullPointerException() {
		try {
			AnalayzeCommand.loginUser(null, "check null");
			fail();
		} catch (NullPointerException e) {}
	}
	
	// checking functionality: password is null - throws NullPointerException
	// input data: (username, password):("check null", null)
	// expected result: NullPointerException
	@Test
	public void login_nullPassword_catchNullPointerException() {
		try {
			AnalayzeCommand.loginUser("check null", null);
			fail();
		} catch (NullPointerException e) {}
	}
	
	// checking functionality: empty Strings - user doesn't exist - returned status is NOT_REGISTERED
	// input data: (username, password):("", "")
	// expected result: HashMap<String, Object> = 	{"response": Status.NOT_REGISTERED}
	@Test
	public void login_emptyStrings_returnStatusNotRegistered() {
		HashMap<String, Object> returnedResponse = AnalayzeCommand.loginUser("", "");
		assertEquals(returnedResponse, expectedResponseNotRegistered);
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		logoutAllUsers();
	}

	/**
	 * Logout all NEW_LOG_IN users - switch them from 1 to 0
	 */
	public static void logoutAllUsers() {
		AnalayzeCommand.logoutUser(userNewLogInCustomerId);
		AnalayzeCommand.logoutUser(userNewLogInNewCustomerId);
		AnalayzeCommand.logoutUser(userNewLogInStoreMangerId);
		AnalayzeCommand.logoutUser(userNewLogInCustomerServiceId);
		AnalayzeCommand.logoutUser(userNewLogInDeliveryCoordinatorId);
		AnalayzeCommand.logoutUser(userNewLogInServiceExpertId);
		AnalayzeCommand.logoutUser(userNewLogInCEOId);
		AnalayzeCommand.logoutUser(userNewLogInMarketingWorkerId);
		AnalayzeCommand.logoutUser(userNewLogInStoreWorkerId);
	}
	
	/**
	 * Set up all expected responses
	 */
	public void setUpExpectedResponses() {
		setUpExpectedFailedResponses();
		setUpCustomerExpectedResponse();
		setUpNewCustomerExpectedResponse();
		setUpStoreMangerExpectedResponse();
		setUpCustomerServiceExpectedResponse();
		setUpMarketingWorkerExpectedResponse();
		setUpServiceExpertExpectedResponse();
		setUpCEOExpectedResponse();
		setUpDeliveryCoordinatorExpectedResponse();
		setUpStoreWorkerExpectedResponse();
	}
	
	public void setUpDeliveryCoordinatorExpectedResponse() {
		expectedResponseNewLogInDeliveryCoordinator.put("response", Status.NEW_LOG_IN);
		expectedResponseNewLogInDeliveryCoordinator.put("idUser", (Integer) 18);
		expectedResponseNewLogInDeliveryCoordinator.put("idAccount", (Integer) 20);
		expectedResponseNewLogInDeliveryCoordinator.put("userType", UserType.DELIVERY_COORDINATOR);
		expectedResponseNewLogInDeliveryCoordinator.put("storeCredit", (Double) 0.0);
	}

	public void setUpCEOExpectedResponse() {
		expectedResponseNewLogInCEO.put("response", Status.NEW_LOG_IN);
		expectedResponseNewLogInCEO.put("idUser", (Integer) 17);
		expectedResponseNewLogInCEO.put("idAccount", (Integer) 17);
		expectedResponseNewLogInCEO.put("userType", UserType.CEO);
		expectedResponseNewLogInCEO.put("storeCredit", (Double) 0.0);
	}

	public void setUpServiceExpertExpectedResponse() {
		expectedResponseNewLogInServiceExpert.put("response", Status.NEW_LOG_IN);
		expectedResponseNewLogInServiceExpert.put("idUser", (Integer) 16);
		expectedResponseNewLogInServiceExpert.put("idAccount", (Integer) 16);
		expectedResponseNewLogInServiceExpert.put("userType", UserType.SERVICE_EXPERT);
		expectedResponseNewLogInServiceExpert.put("storeCredit", (Double) 0.0);
	}

	public void setUpMarketingWorkerExpectedResponse() {
		expectedResponseNewLogInMarketingWorker.put("response", Status.NEW_LOG_IN);
		expectedResponseNewLogInMarketingWorker.put("idUser", (Integer) 9);
		expectedResponseNewLogInMarketingWorker.put("idAccount", (Integer) 9);
		expectedResponseNewLogInMarketingWorker.put("userType", UserType.MARKETING_WORKER);
		expectedResponseNewLogInMarketingWorker.put("storeCredit", (Double) 0.0);
	}

	public void setUpCustomerServiceExpectedResponse() {
		expectedResponseNewLogInCustomerService.put("response", Status.NEW_LOG_IN);
		expectedResponseNewLogInCustomerService.put("idUser", (Integer) 8);
		expectedResponseNewLogInCustomerService.put("idAccount", (Integer) 8);
		expectedResponseNewLogInCustomerService.put("userType", UserType.CUSTOMER_SERVICE);
		expectedResponseNewLogInCustomerService.put("storeCredit", (Double) 0.0);
	}

	public void setUpStoreMangerExpectedResponse() {
		expectedResponseNewLogInStoreManger.put("response", Status.NEW_LOG_IN);
		expectedResponseNewLogInStoreManger.put("idUser", (Integer) 7);
		expectedResponseNewLogInStoreManger.put("idAccount", (Integer) 7);
		expectedResponseNewLogInStoreManger.put("userType", UserType.STORE_MANGER);
		expectedResponseNewLogInStoreManger.put("storeCredit", (Double) 0.0);
	}

	public void setUpNewCustomerExpectedResponse() {
		expectedResponseNewLogInNewCustomer.put("response", Status.NEW_LOG_IN);
		expectedResponseNewLogInNewCustomer.put("idUser", (Integer) 5);
		expectedResponseNewLogInNewCustomer.put("idAccount", (Integer) 5);
		expectedResponseNewLogInNewCustomer.put("userType", UserType.NEW_CUSTOMER);
		expectedResponseNewLogInNewCustomer.put("storeCredit", (Double) 0.0);
	}

	public void setUpCustomerExpectedResponse() {
		expectedResponseNewLogInCustomer.put("response", Status.NEW_LOG_IN);
		expectedResponseNewLogInCustomer.put("idUser", (Integer) 6);
		expectedResponseNewLogInCustomer.put("idAccount", (Integer) 6);
		expectedResponseNewLogInCustomer.put("userType", UserType.CUSTOMER);
		expectedResponseNewLogInCustomer.put("storeCredit", (Double) 0.0);
	}
	
	public void setUpStoreWorkerExpectedResponse() {
		expectedResponseNewLogInStoreWorker.put("response", Status.NEW_LOG_IN);
		expectedResponseNewLogInStoreWorker.put("idUser", (Integer) 19);
		expectedResponseNewLogInStoreWorker.put("idAccount", (Integer) 21);
		expectedResponseNewLogInStoreWorker.put("userType", UserType.STORE_WORKER);
		expectedResponseNewLogInStoreWorker.put("storeCredit", (Double) 0.0);
	}

	public void setUpExpectedFailedResponses() {
		expectedResponseAlreadyLoggedIn.put("response", Status.ALREADY_LOGGED_IN);
		expectedResponseNotRegistered.put("response", Status.NOT_REGISTERED);
		expectedResponseSuspended.put("response", Status.SUSPENDED);
	}

	/**
	 * Set up usernames and passwords for getting responses from AnalayzeCommand
	 */
	public void setUpLogIns() {
		userNotRegistered = "shusty";
		userAlreadyLoggedIn = "yana";
		userSuspended = "hanil";
		userNewLogInCustomer = "dolev";
		userNewLogInNewCustomer = "yael";
		userNewLogInStoreManger = "mng";
		userNewLogInCustomerService = "cs";
		userNewLogInMarketingWorker = "mrkt";
		userNewLogInServiceExpert = "se";
		userNewLogInCEO = "ceo";
		userNewLogInDeliveryCoordinator = "dc";
		userNewLogInStoreWorker = "sw";
	}

	/**
	 * Set up userIds for tear down 
	 */
	public void setUpNewLogInIds() {
		userNewLogInCustomerId = 6;
		userNewLogInNewCustomerId = 5;
		userNewLogInStoreMangerId = 7;
		userNewLogInCustomerServiceId = 8;
		userNewLogInMarketingWorkerId = 9;
		userNewLogInServiceExpertId = 16;
		userNewLogInCEOId = 17;
		userNewLogInDeliveryCoordinatorId = 18;
		userNewLogInStoreWorkerId = 19;
	}
}
