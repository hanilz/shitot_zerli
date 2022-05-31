package entities;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	private Map<ProductsBase, Integer> cart = new HashMap<>(); // saves the product or items and the quantity of the
																// product in the cart
	private static Cart cartInstance = null;

	private double totalPrice = 0;
	
	private Product productToEdit = null;

	private Cart() {
	}

	public static Cart getInstance() {
		return ((cartInstance == null) ? cartInstance = new Cart() : cartInstance);
	}

	/**
	 * This method is used to add/update and item or products in the cart
	 * 
	 * @param product  or item we want to add/update
	 * @param quantity we want to set for the product or item
	 * @return if added/updated successfully
	 */
	public boolean addToCart(ProductsBase product, int quantity, boolean add) {
		if (product == null || quantity < 0)
			return false;
		int deltaQuantity = 0;
		if (product instanceof CustomProduct) {
			cart.put(product, quantity);
			
			calculateTotalPrice(product.getPrice() * quantity);
			return true;
		}
		ProductsBase foundProduct = findByID(product);
		if (quantity == 0)
			removeFromCart(foundProduct);
		else if (add) {
			if (foundProduct == null)
				cart.put(product, quantity);
			else
				cart.put(foundProduct, cart.get(foundProduct) + quantity);
			deltaQuantity = quantity;
		} else {
			deltaQuantity = quantity - cart.get(foundProduct);
			cart.put(foundProduct, quantity);
		}
		calculateTotalPrice(deltaQuantity * product.getPrice());
		return true;
	}

	public void emptyCart() {
		cart.clear();
		totalPrice = 0;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	private void calculateTotalPrice(double amount) {
		totalPrice += amount;
	}

	/**
	 * This method is used to remove an item or product form the cart if it is in
	 * the cart
	 * 
	 * @param product or item we want to remove
	 * @return if item or product was removed successfully
	 */
	public Boolean removeFromCart(ProductsBase product) {
		ProductsBase foundProduct;
		if(product instanceof CustomProduct) 
			foundProduct = findByName(product);
		else
			foundProduct = findByID(product);
		if (!cart.containsKey(foundProduct))
			return false;
		calculateTotalPrice(-(cart.get(foundProduct)) * product.getPrice());
		cart.remove(foundProduct);
		return true;
	}

	private ProductsBase findByName(ProductsBase check) {
		for (ProductsBase product : cart.keySet()) {
			if (product.getName().equals(check.getName()))
				return product;
		}
		return null;		
	}
	
	/**
	 * @return the cart map
	 */
	public Map<ProductsBase, Integer> getCart() {
		return cart;
	}

	private ProductsBase findByID(ProductsBase check) {
		for (ProductsBase product : cart.keySet()) {
			if (check.getId() == product.getId() && product instanceof Product && check instanceof Product)
				return product;
			else if (check.getId() == product.getId() && product instanceof Item && check instanceof Item)
				return product;
		}
		return null;
	}

	public boolean isCartEmpty() {
		return cart.isEmpty();
	}

	public String toString() {
		return "cart: " + cart + "\n";
	}

	public Product getProductToEdit() {
		return productToEdit;
	}

	public void setProductToEdit(Product productToEdit) {
		this.productToEdit = productToEdit;
	}
}
