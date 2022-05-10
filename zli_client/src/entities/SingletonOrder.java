package entities;

public class SingletonOrder extends Order{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static SingletonOrder instance = null;
	
	public static SingletonOrder getInstance() {
		return ((instance == null) ? instance = new SingletonOrder(): instance);
	}
	
	public SingletonOrder() {
		super(0, 0.0, "", "", "", "", null, "", null, "");
	}
}