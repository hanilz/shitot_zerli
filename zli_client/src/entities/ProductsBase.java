package entities;

import java.io.Serializable;

public class ProductsBase implements Serializable, Comparable<ProductsBase> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	protected String name;
	protected String color;
	private double price;
	private String type;
	private String imagePath;
	protected double discount;

	public ProductsBase(int id, double price, double discount) {
		this.id = id;
		this.price = price;
		this.discount = discount;
	}

	public ProductsBase(int id, String name, String color, double price, String type, String imagePath) {
		this.id = id;
		this.name = name;
		this.color = color;
		this.price = price;
		this.type = type;
		this.imagePath = imagePath;
	}

	public ProductsBase(int id, String name, String color, double price, String type, String imagePath,
			double discount) {
		this.id = id;
		this.name = name;
		this.color = color;
		this.price = price;
		this.type = type;
		this.discount = discount;
		this.imagePath = imagePath;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	@Override
	public int compareTo(ProductsBase o) {
		return (int)(o.getDiscount() - discount);
	}
	
    public double calculateDiscount() {
    	return price - price*discount/100;
    }
    
    public boolean isDiscount() {return discount > 0;}
}
