package entities;

import java.io.Serializable;

public class UserDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7153997594908851279L;
	private int idAccount;
	private String firstName, lastName, id, email, phoneNumber, status;
	
	public UserDetails(int idAccount, String firstName, String lastName, String id, String email, String phoneNumber,
			String status) {
		this.idAccount = idAccount;
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.status = status;
	}

	/**
	 * @return the idAccount
	 */
	public int getIdAccount() {
		return idAccount;
	}

	/**
	 * @param idAccount the idAccount to set
	 */
	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
