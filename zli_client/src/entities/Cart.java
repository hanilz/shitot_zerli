package entities;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	private Map<Product,Integer> cart= new HashMap<>();//saves the product and the amount of the product in the cart
	
	/**
	 * This method is used to add/update and item in the cart
	 * @param product we want to add/update
	 * @param amount we want to set for the product
	 * @return if added/updated successfully
	 */
	public Boolean addToCart(Product product,int amount) {
		if(product == null || amount <=0)
			return false;
		cart.put(product, amount);
		return true;
	}
	
	/**
	 * This method is used to remove an item form the cart if it is in the cart
	 * @param product we want to remove
	 * @return if item was removed successfully
	 */
	public Boolean removeFromCart(Product product) {
		if(!cart.containsKey(product))
			return false;
		cart.remove(product);
		return true;
	}

	/**
	 * @return the cart
	 */
	public Map<Product, Integer> getCart() {
		return cart;
	}
	
	public Product findByID(int id) {
		for(Product p : cart.keySet()) {
			if(id == p.getProductID())
				return p;
		}
		return null;
	}
}
