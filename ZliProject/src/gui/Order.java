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

	public String getDOrder() {
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

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setGreetingCard(String greetingCard) {
		this.greetingCard = greetingCard;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setdOrder(String dOrder) {
		this.dOrder = dOrder;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	

}