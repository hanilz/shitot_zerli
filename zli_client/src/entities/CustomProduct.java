package entities;

import java.util.HashMap;

public class CustomProduct extends Product {

	private static final long serialVersionUID = 1L;

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
	
	@Override
	public double calculateDiscount() {
		double totalDiscountedPrice = 0;
		for (Product product : products.keySet())
			totalDiscountedPrice += product.calculateDiscount();
		for (Item item : items.keySet())
			totalDiscountedPrice += item.calculateDiscount();
		 return totalDiscountedPrice;
	}
	
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
