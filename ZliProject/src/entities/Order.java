package entities;

/**
 * for sending as message between client <-> server
 */
public class Order {
	private int orderNumber;
	private double price; 
	private String greetingCard;
	private String color;
	private String dOrder;
	private String shop;
	private String date;
	private String orderDate;
	
	
	/**Initialize the order object
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

	/**Will return the primary key of the order table
	 * @return orderNumber
	 */
	public int getOrderNumber() {
		return orderNumber;
	}

	/**Will return the price of the order
	 * @return price
	 */
	public double getPrice() {
		return price;
	}

	/**Will return the string of the greeting card that the user attached to the order
	 * @return greetingCard
	 */
	public String getGreetingCard() {
		return greetingCard;
	}

	/**Will return the color of the items
	 * @return color
	 */
	public String getColor() {
		return color;
	}

	/**Will return the description of the order if the user chose compose order
	 * @return dOrder
	 */
	public String getDOrder() {
		return dOrder;
	}

	/**Will return string of shop where the order been sent
	 * @return shop
	 */
	public String getShop() {
		return shop;
	}

	/**The hour and the date that the delivery will bring the order to the user
	 * @return date
	 */
	public String getDate() {
		return date;
	}

	/**When the order sent to the server
	 * @return orderDate
	 */
	public String getOrderDate() {
		return orderDate;
	}

	/**Setting the id for orderNumber
	 * @param orderNumber
	 */
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**Setting the price of the order
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**Setting the greetingCard to the order
	 * @param greetingCard
	 */
	public void setGreetingCard(String greetingCard) {
		this.greetingCard = greetingCard;
	}

	/**Setting the color to the order
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**Setting the dOrder of the order
	 * @param dOrder
	 */
	public void setdOrder(String dOrder) {
		this.dOrder = dOrder;
	}

	/**Setting the shop of the order
	 * @param shop
	 */
	public void setShop(String shop) {
		this.shop = shop;
	}

	/**Setting the date of the order
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**Setting the orderDate of the order
	 * @param orderDate
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	

}