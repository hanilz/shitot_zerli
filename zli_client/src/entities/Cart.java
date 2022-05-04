package entities;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	private Map<Product,Integer> cart = new HashMap<>();  // saves the product and the quantity of the product in the cart
	
	private static Cart cartInstance = null;
	
	private Cart() {}
	
	public static Cart getInstance() {
		return ((cartInstance == null) ? cartInstance = new Cart(): cartInstance);
	}
	
	/**
	 * This method is used to add/update and item in the cart
	 * @param product we want to add/update
	 * @param quantity we want to set for the product
	 * @return if added/updated successfully
	 */
	public Boolean addToCart(Product product, int quantity, boolean add) {
		if(product == null || quantity <0)
			return false;
		Product foundProduct = findByID(product.getProductID());
		if(quantity == 0)
			removeFromCart(foundProduct);
		else if(add) {
			if(foundProduct == null)
				cart.put(product, quantity);
			else
				cart.put(foundProduct, cart.get(foundProduct) + quantity);
		}
		else
			cart.put(foundProduct, quantity);
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
	
	private Product findByID(int id) {
		for(Product p : cart.keySet()) {
			if(id == p.getProductID())
				return p;
		}
		return null;
	}
}
