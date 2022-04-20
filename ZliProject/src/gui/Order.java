package gui;

public class Order {
	private String orderNumber, price, greetingCard, color, dOrder, shop, date, orderDate;
	
	public Order(String orderNumber, String price, String greetingCard, String color, String dOrder, String shop,
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

	public String getOrderNumber() {
		return orderNumber;
	}

	public String getPrice() {
		return price;
	}

	public String getGreetingCard() {
		return greetingCard;
	}

	public String getColor() {
		return color;
	}

	public String getdOrder() {
		return dOrder;
	}

	public String getShop() {
		return shop;
	}

	public String getDate() {
		return date;
	}

	public String getOrderDate() {
		return orderDate;
	}

}