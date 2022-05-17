package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Product extends ProductsBase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String flowerType;
	private String productDescription;
	private ArrayList<Item> items;

	// constructor with items
	public Product(int id, String name, String color, double price, String type, String imagePath, String flowerType,
			String productDescription, ArrayList<Item> items) {
		super(id, name, color, price, type, imagePath);
		this.flowerType = flowerType;
		this.productDescription = productDescription;
		this.items = items;
	}
	
	// constructor without items
	public Product(int id, String name, String color, double price, String type, String imagePath, String flowerType,
			String productDescription) {
		super(id, name, color, price, type, imagePath);
		this.flowerType = flowerType;
		this.productDescription = productDescription;
	}
	
	/**
	 * @return the flowerType
	 */
	public String getFlowerType() {
		return flowerType;
	}
	
	/**
	 * @param flowerType the flowerType to set
	 */
	public void setFlowerType(String flowerType) {
		this.flowerType = flowerType;
	}

	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}

	/**
	 * @param productDescription the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
}
