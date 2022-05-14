package entities;

import java.io.Serializable;

public class OrderProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idOrder;
	private Product product;
	private int quantity;
	
	public OrderProduct(int idOrder, Product product, int quantity) {
		this.idOrder = idOrder;
		this.product = product;
		this.quantity = quantity;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
