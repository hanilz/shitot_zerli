package entities;

public class SingletonOrder extends Order{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static SingletonOrder instance = null;
	private Delivery delivery = null;
	private Delivery pickup = null;
	private Branch pickupBranch = null;
	private boolean isGreetingCard = true;
	private String greetingCardTitle = "";
	private String greetingCardFrom = "";
	private String greetingCardTo = "";
	private String greetingCardContent = "";
	private boolean isPickup = false;
	private boolean isExpress = false;
	
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
	
	public void emptySingletonOrder() {
		instance = null;
	}
	
	public void formatGreetingCard() {
		if(isGreetingCard)
			greetingCard = String.format("Title: %s, From: %s, To: %s, Greeting Card: %s", greetingCardTitle,
					greetingCardFrom, greetingCardTo, greetingCardContent);
		else
			greetingCard = "";
	}
	
	public void setGreetingCardFields(String title, String from, String to, String content) {
		greetingCardTitle = title;
		greetingCardFrom = from;
		greetingCardTo = to;
		greetingCardContent = content;
	}

	public boolean getIsGreetingCard() {
		return isGreetingCard;
	}

	public void setIsGreetingCard(boolean isGreetingCard) {
		this.isGreetingCard = isGreetingCard;
	}

	public String getGreetingCardTitle() {
		return greetingCardTitle;
	}
	
	public String getGreetingCardTo() {
		return greetingCardTo;
	}
	
	public String getGreetingCardFrom() {
		return greetingCardFrom;
	}

	public String getGreetingCardContent() {
		return greetingCardContent;
	}

	public boolean getIsPickup() {
		return isPickup;
	}

	public void setIsPickup(boolean isPickup) {
		this.isPickup = isPickup;
	}

	public Delivery getPickup() {
		return pickup;
	}

	public void setPickup(Delivery pickup) {
		this.pickup = pickup;
	}

	public boolean getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(boolean isExpress) {
		this.isExpress = isExpress;
	}

	public Branch getPickupBranch() {
		return pickupBranch;
	}

	public void setPickupBranch(Branch pickupBranch) {
		this.pickupBranch = pickupBranch;
	}
}