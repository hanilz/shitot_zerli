package entities;

import java.util.HashMap;

public class CustomProduct extends Product {

	private static final long serialVersionUID = 1L;

	private HashMap<Product, Integer> products;

	public CustomProduct(int id, String name, String color, double price, String type, String imagePath,
			HashMap<Item, Integer> items, HashMap<Product, Integer> products) {
		super(id, name, color, price, type, imagePath, "Custom", "Custom", items);

		this.products = products;
	}

	public HashMap<Product, Integer> getProducts() {
		return products;
	}

	public void setProducts(HashMap<Product, Integer> products) {
		this.products = products;
	}
}
