package util;

/**
 * ClientDetails saves the information of the client if the client conncted to the zli server.
 *
 */
public class ClientDetails {
	/**
	 * serverIP of the client.
	 */
	private String serverIP;
	/**
	 * clientIP of the client.
	 */
	private String clientIP;
	/**
	 * connection status of the client.
	 */
	private String status;

	public ClientDetails(String serverIP, String clientIP, String status) {
		this.serverIP = serverIP;
		this.clientIP = clientIP;
		this.status = status;
	}

	/**
	 * get the serverIP of the client to display it into the tableview of the server.
	 * @return serverIP
	 */
	public String getServerIP() {
		return serverIP;
	}
	
	/**
	 * get the clientIP of the client to display it into the tableview of the server.
	 * @return clientIP
	 */
	public String getClientIP() {
		return clientIP;
	}
	
	
	/**
	 * get the status of the client to display it into the tableview of the server.
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	

	/**
	 * Setting the status of the connection of the client.
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 *Indication for checking if the client connected.
	 *@return String of the connection details of the client.
	 */
	public String toString() {
		return ("This client details: Server IP: " + serverIP + " Client IP: " + clientIP + " Status: " + status);
	}
}