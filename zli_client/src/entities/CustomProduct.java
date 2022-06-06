package entities;

import java.util.HashMap;

/**
 * CustomProduct
 *
 */
public class CustomProduct extends Product {

	private static final long serialVersionUID = 1L;
	/**
	 * Save the products that custom product contains
	 */
	private HashMap<Product, Integer> products;
	
	public CustomProduct(int id, String name, String color, double price, String type, String imagePath,
			HashMap<Item, Integer> items, HashMap<Product, Integer> products) {
		super(id, name, color, price, type, imagePath, "Beautiful assortment of flowers", "An assortment of items and products", items);

		this.products = products;
	}

	public HashMap<Product, Integer> getProducts() {
		return products;
	}

	public void setProducts(HashMap<Product, Integer> products) {
		this.products = products;
	}
	
	/**
	 *Calculate The total discounted price of the custom product
	 */
	@Override
	public double calculateDiscount() {
		double totalDiscountedPrice = 0;
		for (Product product : products.keySet())
			totalDiscountedPrice += product.calculateDiscount() * products.get(product);
		for (Item item : items.keySet())
			totalDiscountedPrice += item.calculateDiscount() * items.get(item);
		 return totalDiscountedPrice;
	}
	
	/**
	 *check if the custom product contains Items or Product that have discounts
	 */
	@Override
    public boolean isDiscount() {
		for (Product product : products.keySet()) {
			if(product.isDiscount())
				return true;
		}
		for (Item item : items.keySet()) {
			if(item.isDiscount())
				return true;
		}
		return false;
	}
}
