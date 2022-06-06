package entities;

import java.io.Serializable;

/**DeliveryCoordinatorView used to contain all the data used by the delivery coordinator for a single order
 * @author Eitan
 *
 */
public class DeliveryCoordinatorView implements Serializable {

	private static final long serialVersionUID = -3987697842726663499L;
	private int idOrder;
	private String firstName;
	private String lastName;
	private String address;
	private String deliveryDate;
	private int idUser;
	private String deliverytype;
	private int remainingTime;
	private String personalID;

	public DeliveryCoordinatorView(int idOrder, String firstName, String lastName, String address, String deliveryDate,
			int idUser, String deliverytype, int remainingTime,String personalID) {
		this.idOrder = idOrder;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.deliveryDate = deliveryDate;
		this.idUser = idUser;
		this.deliverytype = deliverytype;
		this.remainingTime = remainingTime;
		this.personalID = personalID;
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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getDeliverytype() {
		return deliverytype;
	}

	public void setDeliverytype(String deliverytype) {
		this.deliverytype = deliverytype;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	public String getPersonalID() {
		return personalID;
	}

	public void setPersonalID(String personalID) {
		this.personalID = personalID;
	}

}
