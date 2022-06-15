package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import entities.Branch;
import entities.Complaint;
import gui.ServerFormController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import util.AnalayzeCommand;
import util.ClientDetails;
import util.Commands;
import util.DataBaseController;
import util.GenerateReports;
import util.NotificationType;
import util.ServerMessageController;

/**
 * ServerController extends the superclass AbstractServer for implementing and
 * overriding the functions for our server-client project (zli)
 */
public class ServerController extends AbstractServer implements Runnable {
	/**
	 * complaintsThread that will check if 24 hours passed after the customer
	 * complainted to the employee.
	 */
	Thread complaintsThread;
	/**
	 * reportsThread that will check if it's the end of the month to generate the
	 * reports.
	 */
	Thread reportsThread;
	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	/**
	 * Saving the ip of the server
	 */
	private String ip;

	/**
	 * Saving all the client that connected to the server so the server screen will
	 * present the clients
	 */
	public static final ObservableList<ClientDetails> clients = FXCollections.observableArrayList();

	// singleton
	private static ServerController sc = null;

	private ServerController() {
		super(DEFAULT_PORT);
	}

	/**
	 * Setting the ip of the server base on the data that given from the ServerGUI.
	 * 
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	public static ServerController getServerController() {
		if (sc == null)
			sc = new ServerController();
		return sc;
	}

	/**
	 * This method will connect to the database will the parameters that given by
	 * the user/gui
	 * 
	 * @return
	 */
	public String connectToDB() {
		return DataBaseController.connect();
	}

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @param msg, client
	 */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		ServerMessageController.getServerMessageController().handleMessages(msg, client);
	}

	/**
	 * Disconnect server from the connection and disconnecting all the clients.
	 */
	@SuppressWarnings("deprecation")
	public void disconnectServer() {
		try {
			AnalayzeCommand.disconnectAllUser();
			HashMap<String, Object> message = new HashMap<>();
			message.put("command", Commands.SERVER_DISCONNEDTED);
			sendToAllClients(message); // make all clients go back to main client screen
			System.out.println("disconnected server");
			complaintsThread.stop();
			reportsThread.stop();
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		disconnectAllClients();
	}

	/**
	 * runServer will start listening to the server after clicking on the connect
	 * button with the right information
	 * 
	 * @return message depending on the try-catch
	 * @throws Exception
	 */
	public String runServer() throws Exception {
		if (!isListening())// if server not already running start it
			try {
				listen();
			} catch (Exception e) {
				close();
				System.out.println(e.getMessage());
				return "Can not listen";
			}
		runComplaintsThread();
		generateReports();
		return isListening() ? "Server listening for connections on port " + getPort()
				: "Server has stopped listening for connections.";
	}

	/**
	 * runComplaintsThread runs a task for the thread that pull the complaints of
	 * the customer everyone minute and updates the status if 24 hours pssed to the
	 * notification center.
	 */
	private synchronized void runComplaintsThread() {
		complaintsThread = new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<Complaint> complaintList;
				while (true) {
					complaintList = AnalayzeCommand.getAllComplaintsForServer();
					if (!complaintList.isEmpty()) {
						for (Iterator<Complaint> iterator = complaintList.iterator(); iterator.hasNext();) {
							Complaint comp = iterator.next();
							if (comp.getRemainingMinutes() <= 0)
								util.ServerNotificationManager.sendNotification(comp.getIdUser(),
										NotificationType.COMPLAINT_DUE, comp.getComplaintID());
							else
								iterator.remove();
						}
						AnalayzeCommand.updateComplaintsStatus(complaintList);
					}
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						System.out.println(e);
					}
				}
			}
		});
		complaintsThread.start();
	}

	/**
	 * Create reportsThread to check if it's the end of the month to generate the
	 * reports. If it's not the end of the month, the thread will sleep for one day
	 * and it will check again depending on the current day. else, it will
	 * generateReport and the thread will sleep another day.
	 */
	private synchronized void generateReports() {
		reportsThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
					Date lastDayOfMonth = calendar.getTime();
					Date currentDate = new Date();
					if (lastDayOfMonth.toString().equals(currentDate.toString()))
						if (!isGenerated(currentDate)) {
							genrateReportCommand(currentDate);
						}
					sleepForOneDay(calendar);
				}
			}

		});
		reportsThread.start();
	}

	/**
	 * If it's the end of the month, it will insert into reports table the reports
	 * for each branch.
	 * 
	 * @param currentDate
	 */
	public void genrateReportCommand(Date currentDate) {
		ArrayList<String> reportsType = new ArrayList<String>(
				Arrays.asList("income", "orders", "complaints", "income histogram"));
		ArrayList<Branch> branches = AnalayzeCommand.selectAllBranches();
		GenerateReports.generateNewReports(reportsType, branches, currentDate);
	}

	/**
	 * The function will set the thread to sleep for one day.
	 * 
	 * @param calendar
	 */
	private void sleepForOneDay(Calendar calendar) {
		try {
			Thread.sleep((calendar.get(Calendar.HOUR_OF_DAY) * 3600 + calendar.get(Calendar.MINUTE) * 60
					+ calendar.get(Calendar.SECOND)) * 1000 + calendar.get(Calendar.MILLISECOND));
		} catch (InterruptedException e) {
		}
	}

	/**
	 * The function will check if the server already generated the reports when it's
	 * the end of the month.
	 * 
	 * @param currentDate
	 * @return true or false depeding if the reports are already generated.
	 */
	private boolean isGenerated(Date currentDate) {
		return GenerateReports.isGeneratedReports(currentDate);
	}

	/**
	 * If client is connected, it will update the tableview of clients in the server gui.
	 * If the client is disconnected and he connected again, it will only change the status to "Connected" in the tabelview.
	 *@param client
	 */
	@Override
	protected void clientConnected(ConnectionToClient client) {
		String clientAddress = client.getInetAddress().toString();
		boolean isExists = false;
		for (ClientDetails currentClient : clients) {
			if (currentClient.getClientIP().equals(clientAddress)) {
				System.out.println("Client already exists!");
				currentClient.setStatus("Connected");
				isExists = true;
				ServerFormController.get().refreshClientsTable();
				break;
			}
		}
		if (!isExists) {
			System.out.println("Client connected");
			ClientDetails newClient = new ClientDetails(ip, clientAddress, "Connected");

			clients.add(newClient);
			System.out.println(newClient);
		}
	}

	/**
	 * Hook method called each time a client disconnects. The default implementation
	 * does nothing. The method may be overridden by subclasses but should remains
	 * synchronized.
	 *
	 * @param client the connection with the client.
	 */
	@Override
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		System.out.println("client has disconnected! (from clientDisconnected) ");
	}

	/**
	 * disconnectAllClients helps us disconnect all the clients that connected to
	 * the server
	 */
	private void disconnectAllClients() {
		System.out.println("Disconnecting all clients!");
		for (ClientDetails currentClient : clients)
			disconnectClient(currentClient);
	}

	/**
	 * Updated the clients table in the gui if the client disconnected from the
	 * server.
	 * 
	 * @param client
	 */
	public void disconnectClient(ClientDetails client) {
		String clientAddress = client.getClientIP();
		System.out.println("Hi! I disconnected =)");

		for (ClientDetails currentClient : clients) {
			if (currentClient.getClientIP().equals(clientAddress)) {
				System.out.println("Client " + currentClient.getClientIP() + " found - disconnecting.");
				currentClient.setStatus("Disconnected");
				System.out
						.println("Client " + currentClient.getClientIP() + " status is - " + currentClient.getStatus());
			}
		}
		ServerFormController.get().refreshClientsTable();
	}
}
