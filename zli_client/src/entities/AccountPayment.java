package entities;

import java.io.Serializable;

public class AccountPayment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String fullName;
	private String cardNumber;
	private String cardDate; //format: mm/yy
	private String cardVCC;
	private User user;
	private int idUser=-1;
	
	public AccountPayment(int id, String cardNumber, String cardDate, String cardVCC, User user) {
		this.id = id;
		this.cardNumber = cardNumber;
		this.cardDate = cardDate;
		this.cardVCC = cardVCC;
		this.user = user;
	}
	
	public AccountPayment(String fullName, String cardNumber, String cardDate, String cardVCC, User user) {
		this.fullName = fullName;
		this.cardNumber = cardNumber;
		this.cardDate = cardDate;
		this.cardVCC = cardVCC;
		this.user = user;
	}
	
	public AccountPayment(String fullName, String cardNumber, String cardDate, String cardVCC, int idUser) {
		this.fullName = fullName;
		this.cardNumber = cardNumber;
		this.cardDate = cardDate;
		this.cardVCC = cardVCC;
		this.idUser = idUser;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardDate() {
		return cardDate;
	}

	public void setCardDate(String cardDate) {
		this.cardDate = cardDate;
	}

	public String getCardVCC() {
		return cardVCC;
	}

	public void setCardVCC(String cardVCC) {
		this.cardVCC = cardVCC;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getIdUser() {
		return idUser;
	}

}
