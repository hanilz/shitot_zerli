package ordersView;

import java.io.Serializable;

public class CustomerOrderView implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -970092486061172903L;
	private int orderID;
	private String orderStatus;
	private String deliveryStatus;
	private String orderDate;
	private String deliveryDate;
	private String deliveryType;
	private Double orderPrice;
	
	public CustomerOrderView(int orderID, String orderStatus, String deliveryStatus, String orderDate,
			String deliveryDate, String deliveryType, Double orderPrice) {
		this.orderID = orderID;
		this.orderStatus = orderStatus;
		this.deliveryStatus = deliveryStatus;
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.deliveryType = deliveryType;
		this.orderPrice = orderPrice;
	}

	/**
	 * @return the orderID
	 */
	public int getOrderID() {
		return orderID;
	}

	/**
	 * @param orderID the orderID to set
	 */
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	/**
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the deliveryStatus
	 */
	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	/**
	 * @param deliveryStatus the deliveryStatus to set
	 */
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
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
	 * @return the deliveryDate
	 */
	public String getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}

	/**
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * @return the orderPrice
	 */
	public Double getOrderPrice() {
		return orderPrice;
	}

	/**
	 * @param orderPrice the orderPrice to set
	 */
	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	
}
