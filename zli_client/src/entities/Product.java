package entities;

public class Product {
	private int productID;
	private String productName;
	private String flowerType;
	private String productColor;
	private double productPrice;
	private String productType;
	private String productDescription;
	private String imagePath;

	public Product(int productID, String productName, String flowerType, String productColor, double productPrice,
			String productType, String productDescription, String imagePath) {
		this.productID = productID;
		this.productName = productName;
		this.flowerType = flowerType;
		this.productColor = productColor;
		this.productPrice = productPrice;
		this.productType = productType;
		this.productDescription = productDescription;
		this.imagePath = imagePath;
	}

	/**
	 * @return the productID
	 */
	public int getProductID() {
		return productID;
	}

	/**
	 * @param productID the productID to set
	 */
	public void setProductID(int productID) {
		this.productID = productID;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the flowerType
	 */
	public String getFlowerType() {
		return flowerType;
	}

	/**
	 * @param flowerType the flowerType to set
	 */
	public void setFlowerType(String flowerType) {
		this.flowerType = flowerType;
	}

	/**
	 * @return the productColor
	 */
	public String getProductColor() {
		return productColor;
	}

	/**
	 * @param productColor the productColor to set
	 */
	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}

	/**
	 * @return the productPrice
	 */
	public double getProductPrice() {
		return productPrice;
	}

	/**
	 * @param productPrice the productPrice to set
	 */
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}

	/**
	 * @param productDescription the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
