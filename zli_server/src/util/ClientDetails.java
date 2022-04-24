package util;

public class ClientDetails {
	private String serverIP;
	private String clientIP;
	private String status;

	public ClientDetails(String serverIP, String clientIP, String status) {
		this.serverIP = serverIP;
		this.clientIP = clientIP;
		this.status = status;
	}

	public String getServerIP() {
		return serverIP;
	}
	
	
	public String getClientIP() {
		return clientIP;
	}
	
	
	public String getStatus() {
		return status;
	}
	

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return ("This client details: Server IP: " + serverIP + " Client IP: " + clientIP + " Status: " + status);
	}
}