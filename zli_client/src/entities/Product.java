package entities;

import java.io.Serializable;

public class Product extends ProductsBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private int productID; //
	//private String productName; //
	private String flowerType;
	//private String productColor; //
	//private double productPrice; //
	//private String productType; //
	private String productDescription;
	//private String imagePath; //
	
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

}
