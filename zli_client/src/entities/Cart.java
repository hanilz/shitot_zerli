package entities;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	private Map<ProductsBase, Integer> cart = new HashMap<>(); // saves the product or items and the quantity of the product in the cart

	//private Map<Item, Integer> itemCart = new HashMap<>(); // saves the item and the quantity of the item in the cart

	private static Cart cartInstance = null;

	private double totalPrice = 0;

	private Cart() {
	}

	public static Cart getInstance() {
		return ((cartInstance == null) ? cartInstance = new Cart() : cartInstance);
	}

	/**
	 * This method is used to add/update and item or products in the cart
	 * 
	 * @param product or item we want to add/update
	 * @param quantity we want to set for the product or item
	 * @return if added/updated successfully
	 */
	public boolean addToCart(ProductsBase product, int quantity, boolean add) {
		if (product == null || quantity < 0)
			return false;
		int deltaQuantity = 0;
		ProductsBase foundProduct = findProductByID(product.getId());
		if (quantity == 0)
			removeFromProductCart(foundProduct);
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

	/**
	 * This method is used to add/update and item in the cart
	 * 
	 * @param product  we want to add/update
	 * @param quantity we want to set for the product
	 * @return if added/updated successfully
	 */
	/*public Boolean addToItemCart(Item item, int quantity) {
		if (item == null || quantity < 0)
			return false;
		int deltaQuantity = 0;

		Item foundItem = findItemByID(item.getItemID());
		if (quantity == 0)
			removeFromItemCart(foundItem);
		else {
			if (foundItem == null) {
				itemCart.put(item, quantity);
				deltaQuantity = quantity;
			} else {
				itemCart.put(foundItem, itemCart.get(item)+quantity);
				deltaQuantity = quantity;
			}
		}
		calculateTotalPrice(deltaQuantity * item.getItemPrice());
		return true;
	}*/

	public void emptyCart() {
		cart.clear();
		//itemCart.clear();
		totalPrice = 0;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	private void calculateTotalPrice(double amount) {
		totalPrice += amount;
	}

	/**
	 * This method is used to remove an item or product form the cart if it is in the cart
	 * 
	 * @param product or item we want to remove
	 * @return if item or product was removed successfully
	 */
	public Boolean removeFromProductCart(ProductsBase product) {
		ProductsBase foundProduct = findProductByID(product.getId());
		if (!cart.containsKey(foundProduct))
			return false;
		calculateTotalPrice(-(cart.get(foundProduct)) * product.getPrice());
		cart.remove(foundProduct);
		return true;
	}

	/**
	 * This method is used to remove an item form the cart if it is in the cart
	 * 
	 * @param item we want to remove
	 * @return if item was removed successfully
	 */
	/*public Boolean removeFromItemCart(Item item) {
		Item foundItem = findItemByID(item.getItemID());
		if (!itemCart.containsKey(foundItem))
			return false;
		calculateTotalPrice(-(itemCart.get(foundItem)) * item.getItemPrice());
		itemCart.remove(foundItem);
		return true;
	}*/

	/**
	 * @return the cart map
	 */
	public Map<ProductsBase, Integer> getCart() {
		return cart;
	}

	/*public Map<Item, Integer> getItemCart() {
		return itemCart;
	}*/

	private ProductsBase findProductByID(int id) {
		for (ProductsBase product : cart.keySet()) {
			if (id == product.getId())
				return product;
		}
		return null;
	}

	/*private Item findItemByID(int id) {
		for (Item item : itemCart.keySet()) {
			if (id == item.getItemID()) 
				return item;
		}
		return null;
	}*/

	public boolean isCartEmpty() {
		return cart.isEmpty();
	}

	/*public boolean isItemCartEmpty() {
		return itemCart.isEmpty();
	}*/
	
	
	/*public boolean isEmpty()
	{
		return isProductCartEmpty()&&isItemCartEmpty();
	}*/

	public String toString() {
		return "cart: " + cart + "\n";
	}
}
