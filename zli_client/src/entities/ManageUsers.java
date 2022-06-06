package entities;

import java.io.Serializable;

/**
 * Manage users save specific user details
 * Provides setters and getters
 */
@SuppressWarnings("serial")
public class ManageUsers implements Serializable{
		private int idUser;
		private String firstName;
		private String lastName;
		private String id;
		private String userType;
		private String status;
		
		public ManageUsers(int idUser, String firstName, String lastName, String id, String userType, String status) {
			this.idUser = idUser;
			this.firstName = firstName;
			this.lastName = lastName;
			this.id = id;
			this.userType = userType;
			this.status = status;
		}

		/**
		 * @return the idUser
		 */
		public int getIdUser() {
			return idUser;
		}

		/**
		 * @param idUser the idUser to set
		 */
		public void setIdUser(int idUser) {
			this.idUser = idUser;
		}

		/**
		 * @return the firstName
		 */
		public String getFirstName() {
			return firstName;
		}

		/**
		 * @param firstName the firstName to set
		 */
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		/**
		 * @return the lastName
		 */
		public String getLastName() {
			return lastName;
		}

		/**
		 * @param lastName the lastName to set
		 */
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return the userType
		 */
		public String getUserType() {
			return userType;
		}

		/**
		 * @param userType the userType to set
		 */
		public void setUserType(String userType) {
			this.userType = userType;
		}

		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}
		
}
