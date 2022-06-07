package entities;

import java.io.Serializable;

/**
 * OrderItem saves idOrder,item and quantity
 * Provides setters and getters
 */
public class OrderItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idOrder;
	private Item item;
	private int quantity;
	
	public OrderItem(int idOrder, Item item, int quantity) {
		this.idOrder = idOrder;
		this.item = item;
		this.quantity = quantity;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
