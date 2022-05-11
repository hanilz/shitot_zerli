package entities;

import java.io.Serializable;

public class DeliveriesOrders implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idOrder;
	private int idDelivery;
	
	public DeliveriesOrders(int idOrder, int idDelivery) {
		this.idOrder = idOrder;
		this.idDelivery = idDelivery;
	}

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public int getIdDelivery() {
		return idDelivery;
	}

	public void setIdDelivery(int idDelivery) {
		this.idDelivery = idDelivery;
	}
}
