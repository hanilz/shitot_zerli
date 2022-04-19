package util;
public class ClientDetails {
		private String ip;
		private String client;
		private String status;

		public ClientDetails(String ip, String client, String status) {
			this.ip = ip;
			this.client = client;
			this.status = status;
		}
		
		public String getClient() { return client; }
		
		public void setStatus(String status) {this.status = status;}
		
		public String toString() {return ("This client details: Server IP: " + ip + " Client IP: " + client + " Status: " + status);} 
	}