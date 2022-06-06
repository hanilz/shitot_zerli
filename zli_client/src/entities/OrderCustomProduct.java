package entities;

import java.io.Serializable;

/**
 * OrderCustomProduct saves CustomProduct and attributes
 * Provides setter and Getters
 */
public class OrderCustomProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	private int idOrder;
	private CustomProduct customProduct;
	private int quantity;
	
	public OrderCustomProduct(int idOrder, CustomProduct customProduct, int quantity) {
		this.idOrder = idOrder;
		this.customProduct = customProduct;
		this.quantity = quantity;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public CustomProduct getCustomProduct() {
		return customProduct;
	}

	public void setCustomProduct(CustomProduct customProduct) {
		this.customProduct = customProduct;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
