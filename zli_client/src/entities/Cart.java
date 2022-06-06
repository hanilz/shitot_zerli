package entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Cart save ProductsBase objects
 * can only have one cart(singleton)
 */
public class Cart {
	private Map<ProductsBase, Integer> cart = new HashMap<>(); // saves the product or items and the quantity of the
																// product in the cart
	private static Cart cartInstance = null;
	private double totalPrice = 0;
	private double totalDiscountPrice = 0;
	private Product productToEdit = null;

	private Cart() {
	}

	/**
	 * @return instance of cart(singleton)
	 */
	public static Cart getInstance() {
		return ((cartInstance == null) ? cartInstance = new Cart() : cartInstance);
	}

	/**
	 * This method is used to add/update and item or products in the cart
	 * @param product  or item we want to add/update
	 * @param quantity we want to set for the product or item
	 * @return if added/updated successfully
	 */
	public boolean addToCart(ProductsBase product, int quantity, boolean add) {
		if (product == null || quantity < 0)
			return false;
		ProductsBase foundProduct = null;
		int deltaQuantity = 0;
		if (product instanceof CustomProduct) {
			foundProduct = findByName(product);
		}
		else {
			foundProduct = findByID(product);
		}
		if (quantity == 0) {
			removeFromCart(foundProduct);
		}
		else if (foundProduct == null) {
			cart.put(product, quantity);
			deltaQuantity = quantity;
		}
		else if (add) {
			cart.put(foundProduct, cart.get(foundProduct) + quantity);
			deltaQuantity = quantity;
		} else {
			deltaQuantity = quantity - cart.get(foundProduct);
			cart.put(foundProduct, quantity);
		}
		calculateTotalPrice(deltaQuantity * product.getPrice());
		calculateTotalDiscountPrice(deltaQuantity * product.calculateDiscount());
		return true;
	}

	/**
	 * Remove all objects from cart and reset total price
	 */
	public void emptyCart() {
		cart.clear();
		totalPrice = 0;
		totalDiscountPrice = 0;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public double getTotalDiscountPrice() {
		return totalDiscountPrice;
	}
	
	/**
	 * add amount to total price
	 * @param amount
	 */
	private void calculateTotalPrice(double amount) {
		totalPrice += amount;
	}
	
	/**
	 * add amount to total discount price
	 * @param amount
	 */
	private void calculateTotalDiscountPrice(double amount) {
		totalDiscountPrice += amount;
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
		calculateTotalDiscountPrice(-(cart.get(foundProduct)) * product.calculateDiscount());
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

	/**
	 * searching object in cart by its ProductsBase id
	 * @param check
	 * @return ProductsBase if found else null
	 */
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
