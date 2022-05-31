package entities;

import java.io.Serializable;

public class Delivery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idDelivery; // PK
	private String address;
	private String receiverName;
	private String phoneNumber;
	private String deliveryDate;
	private String status; // we will change it later to enum
	private String type;

	public Delivery(int idDelivery, String address, String receiverName, String phoneNumber, String deliveryDate,
			String status, String type) {
		this.idDelivery = idDelivery;
		this.address = address;
		this.receiverName = receiverName;
		this.phoneNumber = phoneNumber;
		this.deliveryDate = deliveryDate;
		this.status = status;
		this.type = type;
	}

	public Delivery(String address, String receiverName, String phoneNumber, String deliveryDate, String status,
			String type) {
		this.address = address;
		this.receiverName = receiverName;
		this.phoneNumber = phoneNumber;
		this.deliveryDate = deliveryDate;
		this.status = status;
		this.type = type;
	}
	
	// for pickup
	public Delivery(String deliveryDate, String status, String type) {
		this.deliveryDate = deliveryDate;
		this.status = status;
		this.type = type;
	}

	public int getIdDelivery() {
		return idDelivery;
	}

	public void setIdDelivery(int idDelivery) {
		this.idDelivery = idDelivery;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
