package entities;

import java.io.Serializable;

public class Order implements Serializable{
	/**
	 * for sending as message between client <-> server
	 */
	private static final long serialVersionUID = 2609520809176257748L;
	private int orderNumber;
	private double price; 
	private String greetingCard;
	private String color;
	private String dOrder;
	private String shop;
	private String date;
	private String orderDate;
	
	
	/**
	 * @param orderNumber
	 * @param price
	 * @param greetingCard
	 * @param color
	 * @param dOrder
	 * @param shop
	 * @param date
	 * @param orderDate
	 */
	public Order(int orderNumber, double price, String greetingCard, String color, String dOrder, String shop,
			String date, String orderDate) {
		this.orderNumber = orderNumber;
		this.price = price;
		this.greetingCard = greetingCard;
		this.color = color;
		this.dOrder = dOrder;
		this.shop = shop;
		this.date = date;
		this.orderDate = orderDate;
	}

	/**
	 * @return
	 */
	public int getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @return
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @return
	 */
	public String getGreetingCard() {
		return greetingCard;
	}

	/**
	 * @return
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @return
	 */
	public String getDOrder() {
		return dOrder;
	}

	/**
	 * @return
	 */
	public String getShop() {
		return shop;
	}

	/**
	 * @return
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @return
	 */
	public String getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderNumber
	 */
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return
	 */
	public void setGreetingCard(String greetingCard) {
		this.greetingCard = greetingCard;
	}

	/**
	 * @return
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return
	 */
	public void setdOrder(String dOrder) {
		this.dOrder = dOrder;
	}

	/**
	 * @return
	 */
	public void setShop(String shop) {
		this.shop = shop;
	}

	/**
	 * @return
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	

}