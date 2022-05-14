package entities;

import java.io.Serializable;

/**
 * for sending as message between client <-> server
 */
public class Order implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idOrder;
	private double price; 
	private String greetingCard;
	private String dOrder;
	private Branch branch;
	private String date;
	private String status;
	private User user;
	private String paymentMethod;
	
	public Order(int idOrder, double price, String greetingCard, String dOrder,
			String date, Branch branch, String status, User user, String paymentMethod) {
		this.idOrder = idOrder;
		this.price = price;
		this.greetingCard = greetingCard;
		this.dOrder = dOrder;
		this.branch = branch;
		this.date = date;
		this.paymentMethod = paymentMethod;
		this.user = user;
		this.status = status;
	}
	
	public Order(double price, String greetingCard, String dOrder, Branch branch, String status, String paymentMethod, User user) {
		this.price = price;
		this.greetingCard = greetingCard;
		this.dOrder = dOrder;
		this.branch = branch;
		this.paymentMethod = paymentMethod;
		this.user = user;
		this.status = status;
	}
	
	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getGreetingCard() {
		return greetingCard;
	}

	public void setGreetingCard(String greetingCard) {
		this.greetingCard = greetingCard;
	}

	public String getdOrder() {
		return dOrder;
	}

	public void setdOrder(String dOrder) {
		this.dOrder = dOrder;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}