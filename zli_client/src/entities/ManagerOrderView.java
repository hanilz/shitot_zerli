package entities;

import java.io.Serializable;


/**
 * ManagerOrderView saves view a row of orders
 * Provides setters and getters
 */
public class ManagerOrderView implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idOrder;
	private Double price;
	private String firstName;
	private String lastName;
	private String status;
	private int idUser;
	private String orderDate;
	private String deliveryTime;
	private String deliveryType;
	private String lastModified;
	private int timeTillDelivery;
	private String timeTillDeliveryString;
	
	public ManagerOrderView(int idOrder, Double price, String firstName, String lastName, String status, int idUser,
			String orderDate, String deliveryTime, String deliveryType, String lastModified, int timeTillDelivery) {
		this.idOrder = idOrder;
		this.price = price;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.idUser = idUser;
		this.orderDate = orderDate;
		this.deliveryTime = deliveryTime;
		this.deliveryType = deliveryType;
		this.lastModified = lastModified;
		this.timeTillDelivery = timeTillDelivery;
		timeTillDeliveryString = convertToString(timeTillDelivery);
	}
	private String convertToString(int timeTillDelivery) {
		int minutes = timeTillDelivery%60;
		int hours = timeTillDelivery/60;
		return hours+":"+minutes;
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
	 * @return the orderDate
	 */
	public String getOrderDate() {
		return orderDate;
	}
	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	/**
	 * @return the deliveryTime
	 */
	public String getDeliveryTime() {
		return deliveryTime;
	}
	/**
	 * @param deliveryTime the deliveryTime to set
	 */
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	/**
	 * @return the lastModified
	 */
	public String getLastModified() {
		return lastModified;
	}
	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	/**
	 * @return the timeTillDelivery
	 */
	public int getTimeTillDelivery() {
		return timeTillDelivery;
	}
	/**
	 * @param timeTillDelivery the timeTillDelivery to set
	 */
	public void setTimeTillDelivery(int timeTillDelivery) {
		this.timeTillDelivery = timeTillDelivery;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getTimeTillDeliveryString() {
		return timeTillDeliveryString;
	}
	public void setTimeTillDeliveryString(String timeTillDeliveryString) {
		this.timeTillDeliveryString = timeTillDeliveryString;
	}
	
	
}
