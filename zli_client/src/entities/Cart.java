package entities;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	private Map<Product,Integer> productCart = new HashMap<>();  // saves the product and the quantity of the product in the cart
	
	private Map<Item,Integer> itemCart = new HashMap<>();  // saves the item and the quantity of the item in the cart
	
	private static Cart cartInstance = null;
	
	private double totalPrice = 0;
	
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
	public Boolean addToProductCart(Product product, int quantity, boolean add) {
		if(product == null || quantity < 0)
			return false;
		int deltaQuantity = 0;
		Product foundProduct = findProductByID(product.getProductID());
		if(quantity == 0)
			removeFromProductCart(foundProduct);
		else if(add) {
			if(foundProduct == null) 
				productCart.put(product, quantity);
			else 
				productCart.put(foundProduct, productCart.get(foundProduct) + quantity);
			deltaQuantity = quantity;
		}
		else {
			deltaQuantity = quantity - productCart.get(foundProduct);
			productCart.put(foundProduct, quantity);
		}
		calculateTotalPrice(deltaQuantity * product.getProductPrice());
		return true;
	}
	
	/**
	 * This method is used to add/update and item in the cart
	 * @param product we want to add/update
	 * @param quantity we want to set for the product
	 * @return if added/updated successfully
	 */
	public Boolean addToItemCart(Item item, int quantity) {
		if(item == null || quantity < 0)
			return false;
		int deltaQuantity = 0;
		Item foundItem = findItemByID(item.getItemID());
		if(quantity == 0)
			removeFromItemCart(foundItem);
		else {
			if(foundItem == null) {
				itemCart.put(item, quantity);
				deltaQuantity = quantity - itemCart.get(item);
			}
			else {
				itemCart.put(foundItem, quantity);
				deltaQuantity = quantity - itemCart.get(foundItem);
			}
		}
		calculateTotalPrice(deltaQuantity * item.getItemPrice());
		return true;
	}
	
	public void emptyCart() {
		productCart.clear();
		itemCart.clear();
		totalPrice = 0;
	}
	
	public double getTotalPrice() { return totalPrice;}
	
	private void calculateTotalPrice(double amount) {
		totalPrice += amount;
	}
			
	/**
	 * This method is used to remove an item form the cart if it is in the cart
	 * @param product we want to remove
	 * @return if item was removed successfully
	 */
	public Boolean removeFromProductCart(Product product) {
		Product foundProduct = findProductByID(product.getProductID());
		if(!productCart.containsKey(foundProduct))
			return false;
		calculateTotalPrice(-(productCart.get(foundProduct)) * product.getProductPrice());

		productCart.remove(foundProduct);
		return true;
	}
	
	/**
	 * This method is used to remove an item form the cart if it is in the cart
	 * @param item we want to remove
	 * @return if item was removed successfully
	 */
	public Boolean removeFromItemCart(Item item) {
		Item foundItem = findItemByID(item.getItemID());
		if(!itemCart.containsKey(foundItem))
			return false;
		calculateTotalPrice(-(itemCart.get(foundItem)) * item.getItemPrice());

		itemCart.remove(foundItem);
		return true;
	}

	/**
	 * @return the product cart
	 */
	public Map<Product, Integer> getProductCart() {
		return productCart;
	}
	
	public Map<Item, Integer> getItemCart() {
		return itemCart;
	}
	
	private Product findProductByID(int id) {
		for(Product product : productCart.keySet()) {
			if(id == product.getProductID())
				return product;
		}
		return null;
	}
	
	private Item findItemByID(int id) {
		for(Item item : itemCart.keySet()) {
			if(id == item.getItemID())
				return item;
		}
		return null;
	}

	public boolean isProductCartEmpty()
	{
		return productCart.isEmpty();
	}
	
	public boolean isItemCartEmpty()
	{
		return itemCart.isEmpty();
	}

}
