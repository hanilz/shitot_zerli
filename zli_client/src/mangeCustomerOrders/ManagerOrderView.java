package mangeCustomerOrders;

import java.io.Serializable;

//this class is used to view a row of orders in the manager screen
public class ManagerOrderView implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idOrder;
	private Double price;
	private String firstName;
	private String lastName;
	private String date;
	private String status;

	public ManagerOrderView(int idOrder, Double price, String firstName, String lastName, String date, String status) {
		this.idOrder = idOrder;
		this.price = price;
		this.firstName = firstName;
		this.lastName = lastName;
		this.date = date;
		this.status = status;
	}

	/**
	 * @return the idOrder
	 */
	public int getIdOrder() {
		return idOrder;
	}

	/**
	 * @param idOrder the idOrder to set
	 */
	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
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
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
