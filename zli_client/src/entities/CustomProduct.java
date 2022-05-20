package entities;

import java.util.ArrayList;

public class CustomProduct extends ProductsBase{

	private static final long serialVersionUID = 1L;

	private ArrayList<Item> items;
	
	private ArrayList<Product> products;
	
	public CustomProduct(int id, String name, String color, double price, String type, String imagePath, ArrayList<Item> items) {
		super(id, name, color, price, type, imagePath);
		
		this.items = items;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

}
