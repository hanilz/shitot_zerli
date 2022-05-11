package entities;

public class SingletonDelivery extends Delivery {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static SingletonDelivery deliveryInstance = null;
	
	private SingletonDelivery() {
		super(0, "", "", "", "", "");
		// TODO Auto-generated constructor stub
	}
	
	public static SingletonDelivery getDeliveryInstance() {
		if(deliveryInstance == null)
			deliveryInstance = new SingletonDelivery();
		return deliveryInstance;
	}

}
