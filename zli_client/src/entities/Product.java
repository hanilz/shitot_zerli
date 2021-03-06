package entities;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Product saves flowerType,productDescription and items
 * Provides setters and getters
 */
public class Product extends ProductsBase implements Serializable {
	private static final long serialVersionUID = 1L;

	private String flowerType;
	private String productDescription;
	protected HashMap<Item, Integer> items;
	
	public Product(int id, double price, double discount, String productDescription) {
		super(id, price, discount);
		this.productDescription = productDescription;
	}

	/**constructor with items
	 * @param id
	 * @param name
	 * @param color
	 * @param price
	 * @param type
	 * @param imagePath
	 * @param discount
	 * @param flowerType
	 * @param productDescription
	 * @param items
	 */
	public Product(int id, String name, String color, double price, String type, String imagePath, double discount, String flowerType,
			String productDescription, HashMap<Item, Integer> items) {
		super(id, name, color, price, type, imagePath,discount);
		this.flowerType = flowerType;
		this.productDescription = productDescription;
		this.items = items;
	}
	
	// constructor with items
	public Product(int id, String name, String color, double price, String type, String imagePath, String flowerType,
			String productDescription, HashMap<Item, Integer> items) {
		super(id, name, color, price, type, imagePath);
		this.flowerType = flowerType;
		this.productDescription = productDescription;
		this.items = items;
	}

	// constructor without items
	public Product(int id, String name, String color, double price, String type, String imagePath, double discount, String flowerType,
			String productDescription) {
		super(id, name, color, price, type, imagePath, discount);
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

	public HashMap<Item, Integer> getItems() {
		return items;
	}

	public void setItems(HashMap<Item, Integer> items) {
		this.items = items;
	}
}
