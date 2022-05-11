package entities;

public class SingletonOrder extends Order{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static SingletonOrder instance = null;
	private Delivery delivery = null;

	public static SingletonOrder getInstance() {
		return ((instance == null) ? instance = new SingletonOrder(): instance);
	}
	
	public SingletonOrder() {
		super(0, 0.0, "", "", "", null, "", null, "");
	}
	
	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
}