package entities;

import java.io.Serializable;

public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int itemID;
	private String itemName;
	private String itemColor;
	private double itemPrice;
	private String itemType;
	private String imagePath;

	public Item(int itemID, String itemName, String itemColor, double itemPrice,
			String itemType, String imagePath) {
		this.itemID = itemID;
		this.itemName = itemName;
		this.itemColor = itemColor;
		this.itemPrice = itemPrice;
		this.itemType = itemType;
		this.imagePath = imagePath;
	}

	/**
	 * @return the productID
	 */
	public int getItemID() {
		return itemID;
	}

	/**
	 * @param productID the productID to set
	 */
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	/**
	 * @return the productName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the productColor
	 */
	public String getItemColor() {
		return itemColor;
	}

	/**
	 * @param productColor the productColor to set
	 */
	public void setItemColor(String itemColor) {
		this.itemColor = itemColor;
	}

	/**
	 * @return the productPrice
	 */
	public double getItemPrice() {
		return itemPrice;
	}

	/**
	 * @param productPrice the productPrice to set
	 */
	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	/**
	 * @return the productType
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
