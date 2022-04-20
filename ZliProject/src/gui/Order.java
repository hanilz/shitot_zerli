package gui;

import javafx.beans.property.SimpleStringProperty;

public class Order {
	SimpleStringProperty orderNumber, price, greetingCard, color, dOrder, shop, date, orderDate;

	Order(String orderNumber, String price, String greetingCard, String color, String dOrder, String shop, String date,
			String orderDate) {
		this.orderNumber = new SimpleStringProperty(orderNumber);
		this.price = new SimpleStringProperty(price);
		this.greetingCard = new SimpleStringProperty(greetingCard);
		this.color = new SimpleStringProperty(color);
		this.dOrder = new SimpleStringProperty(dOrder);
		this.shop = new SimpleStringProperty(shop);
		this.date = new SimpleStringProperty(date);
		this.orderDate = new SimpleStringProperty(orderDate);
	}

	public SimpleStringProperty getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(SimpleStringProperty orderNumber) {
		this.orderNumber = orderNumber;
	}

	public SimpleStringProperty getPrice() {
		return price;
	}

	public void setPrice(SimpleStringProperty price) {
		this.price = price;
	}

	public SimpleStringProperty getGreetingCard() {
		return greetingCard;
	}

	public void setGreetingCard(SimpleStringProperty greetingCard) {
		this.greetingCard = greetingCard;
	}

	public SimpleStringProperty getColor() {
		return color;
	}

	public void setColor(SimpleStringProperty color) {
		this.color = color;
	}

	public SimpleStringProperty getdOrder() {
		return dOrder;
	}

	public void setdOrder(SimpleStringProperty dOrder) {
		this.dOrder = dOrder;
	}

	public SimpleStringProperty getShop() {
		return shop;
	}

	public void setShop(SimpleStringProperty shop) {
		this.shop = shop;
	}

	public SimpleStringProperty getDate() {
		return date;
	}

	public void setDate(SimpleStringProperty date) {
		this.date = date;
	}

	public SimpleStringProperty getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(SimpleStringProperty orderDate) {
		this.orderDate = orderDate;
	}

}