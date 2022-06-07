package customProduct;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import entities.Cart;
import entities.CustomProduct;
import entities.Item;
import entities.Product;
import entities.ProductsBase;
import inputs.InputChecker;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import util.ManageData;
import util.ManageScreens;
import util.Screens;

/**
 * This class displays the "Create custom product" screen. Using this screen,
 * the customer can select products or items for creating custom product.
 */
public class CustomProductBuilderController implements Initializable {

	@FXML
	private Button addToCartButton;

	@FXML
	private Button backToCatalogButton;

	@FXML
	private CheckBox carnationFilterCheckBox;

	@FXML
	private Label catalogLbl;

	@FXML
	private Label warningLabel;

	@FXML
	private CheckBox chocolateFilterCheckBox;

	@FXML
	private ScrollPane customProductOverviewScrollPane;

	@FXML
	private ScrollPane customProductScrollPane;

	@FXML
	private CheckBox daffodilFilterCheckBox;

	@FXML
	private CheckBox flowerFilterCheckBox;

	@FXML
	private ImageView homeImage;

	@FXML
	private HBox totalPriceHBox;

	@FXML
	private CheckBox lilyFilterCheckBox;

	@FXML
	private CheckBox orchidFilterCheckBox;

	@FXML
	private CheckBox peonyFilterCheckBox;

	@FXML
	private CheckBox pinklFilterCheckBox;

	@FXML
	private CheckBox redFilterCheckBox;

	@FXML
	private CheckBox roseFilterCheckBox;

	@FXML
	private Text totalPriceLabel;

	@FXML
	private CheckBox whiteFilterCheckBox;

	@FXML
	private CheckBox yellowFilterCheckBox;

	@FXML
	private ImageView addToCartImage;

	/**
	 * For display the total price with discount if the customer selected
	 * products/items with discount.
	 */
	private Label totalDiscountPriceLabel = new Label();

	private static CustomProductBuilderController customControllerInstance;

	/**
	 * For display to the customer the items/products that he selected.
	 */
	private VBox overViewVBox = new VBox();
	/**
	 * For display all the items in the catalog to the current screen.
	 */
	private HashMap<Item, Integer> items = new HashMap<>();
	/**
	 * For display all the products in the catalog to the current screen.
	 */
	private HashMap<Product, Integer> products = new HashMap<>();
	/**
	 * After the customer selected the product, he can edit the amount of the
	 * current product that he selected.
	 */
	private Product productToEdit;

	/**
	 * This method adding the custom product to the cart. It displays a popup that
	 * he can view cart or go back to catalog. If no products selected from the
	 * current screen, it will display an alert to the customer.
	 */
	void setAddToCartButton() {
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (overViewVBox.getChildren().size() == 0) { // if no items/products were selected
					switchFillAllFields(); // alert the user
					return;
				}
				getAllProductsFromOverview();
				CustomProduct customProduct = new CustomProduct(0, "Custom Product " + ManageData.customProductNumber++,
						"Custom", getTotalPriceFromOverview(), "Custom", "/resources/catalog/customProductImage.png",
						items, products);

				Cart.getInstance().addToCart(customProduct, 1, true);
				System.out.println("Cart is: " + Cart.getInstance().getCart());
				System.out.println(customProduct.getName() + " added to cart woohoooo");

				try {
					ManageScreens.openPopupFXML(getClass().getResource("CustomProductSuccessfulPopup.fxml"),
							"Custom Product Successful!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		addToCartButton.setOnAction(event);
	}

	/**
	 * This method Opens an alert for selecting products/items to the custom
	 * product.
	 */
	private void switchFillAllFields() {
		warningLabel.setText("* Please select some products first");
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> warningLabel.setText(""));
		pause.play();
	}

	/**
	 * This method getting all the product from the voerview to create custom
	 * product for the customer.
	 */
	private void getAllProductsFromOverview() {
		for (Node currentNode : overViewVBox.getChildren()) {
			if (currentNode instanceof SelectedHBox) {
				SelectedHBox currentSelected = (SelectedHBox) currentNode;
				ProductsBase currentProductsBase = currentSelected.getProduct();
				if (currentProductsBase instanceof Product) {
					Product currentProduct = (Product) currentProductsBase;
					products.put(currentProduct, currentSelected.getQuantity());
				} else { // is Item
					Item currentItem = (Item) currentProductsBase;
					items.put(currentItem, currentSelected.getQuantity());
				}
			}
		}
	}

	/**
	 * This method changing screen to catalog if the customer pressed on the button.
	 */
	void setBackToCatalogButton() {
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ManageScreens.changeScreenTo(Screens.CATALOG);
			}
		};
		backToCatalogButton.setOnAction(event);
	}

	/**
	 * This method chaning the screen to the home screen if the customer pressed on
	 * the button.
	 * 
	 * @param MouseEvent
	 */
	@FXML
	void changeToHomeScreen(MouseEvent event) {
		Cart.getInstance().setProductToEdit(null);
		ManageScreens.home();
	}

	/**
	 * This method initialize the "Create custom product" screen. It sets the button
	 * to the screen, init the items and the products so the customer can create
	 * custom product.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		totalPriceLabel.setText(InputChecker.price(0));
		totalPriceLabel.setStyle("-fx-font-size: 20px;\r\n"
				+ "	-fx-font-weight: bold;");
		overViewVBox.setId("overViewVBox");
		setAddToCartButton();
		setBackToCatalogButton();
		setCheckBoxToFalse();
		customProductScrollPane.setContent(ManageData.customSelectorVBox);
		customControllerInstance = this;
		if (Cart.getInstance().getProductToEdit() != null)
			initEditProduct();
	}

	private void initEditProduct() {
		productToEdit = Cart.getInstance().getProductToEdit();
		selectProductItems();
		if (productToEdit instanceof CustomProduct)
			selectCustomProductProducts();
		updateTotalPrice();
		changeBackToCatalogButtonEvent();
		changeAddToCartButtonEvent();
	}

	/**
	 * This method init the selected items by the customer in the hbox of the custom
	 * product overview.
	 */
	private void selectProductItems() {
		for (Item currentItem : productToEdit.getItems().keySet()) {
			for (Node current : ManageData.customSelectorVBox.getChildren()) {
				if (current instanceof SelectorHBox) {
					SelectorHBox currentSelector = (SelectorHBox) current;
					if (currentSelector.getProduct().getId() == currentItem.getId()
							&& currentSelector.getProduct() instanceof Item) {
						currentSelector.getSelected().setSelected(true);
						SelectedHBox selectedHBox = new SelectedHBox(currentSelector.getProduct(),
								productToEdit.getItems().get(currentItem));
						selectedHBox.initHBox();
						currentSelector.setSelectedHBox(selectedHBox);
						CustomProductBuilderController.updateOverViewVBox("add to overview", selectedHBox);
					}
				}
			}
		}
	}

	/**
	 * This method init the selected products by the customer in the hbox of the
	 * custom product overview.
	 */
	private void selectCustomProductProducts() {
		CustomProduct customProductToEdit = (CustomProduct) productToEdit;
		for (Product currentProduct : customProductToEdit.getProducts().keySet()) {
			for (Node current : ManageData.customSelectorVBox.getChildren()) {
				if (current instanceof SelectorHBox) {
					SelectorHBox currentSelector = (SelectorHBox) current;
					if (currentSelector.getProduct().getId() == currentProduct.getId()
							&& currentSelector.getProduct() instanceof Product) {
						currentSelector.getSelected().setSelected(true);
						SelectedHBox selectedHBox = new SelectedHBox(currentSelector.getProduct(),
								customProductToEdit.getProducts().get(currentProduct));
						selectedHBox.initHBox();
						currentSelector.setSelectedHBox(selectedHBox);
						CustomProductBuilderController.updateOverViewVBox("add to overview", selectedHBox);
					}
				}
			}
		}
	}

	/**
	 * After adding the custom product to the cart, if the customer selected the
	 * view cart, it will change to cart screen.
	 */
	private void changeBackToCatalogButtonEvent() {
		backToCatalogButton.setText("Back To Cart");
		backToCatalogButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Cart.getInstance().setProductToEdit(null); // cancel edit
				ManageScreens.changeScreenTo(Screens.CART);
			}
		});
	}

	/**
	 * This method checking 2 scenrios: If the customer editing the quantity of each
	 * product/items in custom product through the cart. If the customer editing the
	 * product through the cart - it will display the custom product screen to edit
	 * the product.
	 */
	private void changeAddToCartButtonEvent() {
		if (productToEdit instanceof CustomProduct) {
			addToCartButton.setText("Save Changes");
			addToCartImage.setImage(new Image("/resources/icons/saveIcon.png"));
		} else {
			addToCartButton.setText("Edit Product");
			addToCartImage.setImage(new Image("/resources/icons/addToCartIcon.png"));
		}
		addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (overViewVBox.getChildren().size() == 0) { // if no items/products were selected
					switchFillAllFields(); // alert the user
					return;
				}
				getAllProductsFromOverview();
				String name;
				if (productToEdit instanceof CustomProduct || productToEdit.getName().contains("Edited"))
					name = productToEdit.getName();
				else
					name = "Edited " + productToEdit.getName();
				CustomProduct editedCustomProduct = new CustomProduct(0, name, "Custom", getTotalPriceFromOverview(),
						"Your favorite assortment of beautiful flowers", "/resources/catalog/customProductImage.png",
						items, products);

				Cart.getInstance().removeFromCart(Cart.getInstance().getProductToEdit());
				Cart.getInstance().addToCart(editedCustomProduct, 1, true);
				System.out.println("Cart is: " + Cart.getInstance().getCart());
				System.out.println("Edited custom product WOOHOOOOO");
				try {
					Cart.getInstance().setProductToEdit(null);
					ManageScreens.openPopupFXML(getClass().getResource("ProductEditSuccessfulPopup.fxml"),
							"Product Edited Successfully!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * This method updated the overview of the custom product by the products/items
	 * that the customer selected in the screen.
	 * 
	 * @param command
	 * @param overViewSelected
	 */
	public static void updateOverViewVBox(String command, SelectedHBox overViewSelected) {
		customControllerInstance.updateOverviewScrollPane(command, overViewSelected);
	}

	/**
	 * This method checks if the customer selected products/items, it will change
	 * the checkbox to true and add the product/items to the overview hbox.
	 * 
	 * @param command
	 * @param overViewSelected
	 */
	public void updateOverviewScrollPane(String command, SelectedHBox overViewSelected) {
		if (command.equals("add to overview")) {
			addProductIntoOverViewVBox(overViewSelected);
			customProductOverviewScrollPane.setContent(overViewVBox);
		} else {
			overViewVBox.getChildren().clear();
			removeFromOverView(overViewSelected);
			checkSelectedInSelector();
			customProductOverviewScrollPane.setContent(overViewVBox);
		}
	}

	/**
	 * This method adding the product in the overviewHBox after the customer
	 * selected the product that he wants in the custom prodct.
	 * 
	 * @param overViewSelected
	 */
	private void addProductIntoOverViewVBox(SelectedHBox overViewSelected) {
		if (!overViewVBox.getChildren().contains(overViewSelected) && overViewSelected != null) {
			overViewVBox.getChildren().add(overViewSelected);
			updateTotalPrice();
		}
	}

	/**
	 * This method checking the checkbox that selected in the selectorHBox (where
	 * all the prodcuts/items that customer can select).
	 */
	private void checkSelectedInSelector() {
		for (Node current : ManageData.customSelectorVBox.getChildren()) {
			if (current instanceof SelectorHBox) {
				SelectorHBox currentSelector = (SelectorHBox) current;
				if (currentSelector.getSelected().isSelected())
					overViewVBox.getChildren().add(currentSelector.getSelectedHBox());
			}
		}
		updateTotalPrice();
	}

	/**
	 * This method removes items/product if the customer decided to remove the
	 * product/items that he selected.
	 * 
	 * @param overViewSelected
	 */
	private void removeFromOverView(SelectedHBox overViewSelected) {
		for (Node current : ManageData.customSelectorVBox.getChildren()) {
			if (current instanceof SelectorHBox) {
				SelectorHBox currentSelector = (SelectorHBox) current;
				if (overViewSelected != null && currentSelector.getSelectedHBox() != null) {
					if (currentSelector.getSelectedHBox().equals(overViewSelected)
							&& currentSelector.getSelected().isSelected()) {
						currentSelector.getSelected().setSelected(false);
						break;
					}
				}
			}
		}
	}

	/**
	 * Setting the checkbox to false if the customer removed product/item from the
	 * overview in "Create Custom Product".
	 */
	private void setCheckBoxToFalse() {
		for (Node currentHBox : ManageData.customSelectorVBox.getChildren()) {
			if (currentHBox instanceof SelectorHBox) {
				SelectorHBox currentSelector = (SelectorHBox) currentHBox;
				currentSelector.getSelected().setSelected(false);
			}
		}
	}

	/**
	 * This method will update the total price by using the updaeTotalPrice that
	 * updates the total price depending on the products/items that selected by the
	 * customer.
	 */
	public static void updateTotalPriceLabel() {
		customControllerInstance.updateTotalPrice();
	}

	/**
	 * This method updates the total price depending on the products/items that
	 * selected by the customer. If the customer selected products/items with
	 * discount, it will update will a discount price and original price.
	 */
	private void updateTotalPrice() {
		double totalPrice = getTotalPriceFromOverview();
		totalPriceLabel.setText(InputChecker.price(totalPrice));
		totalPriceLabel.setStyle("-fx-font-size: 20px;\r\n"
				+ "	-fx-font-weight: bold;");
		if (isDiscount()) {
			double totalDiscountPrice = getTotalDiscountPriceFromOverview();
			totalDiscountPriceLabel.setText(InputChecker.price(totalDiscountPrice));
			totalDiscountPriceLabel.setId("discountLabel");
			totalPriceLabel.setStyle("-fx-strikethrough: true;\r\n" + "	-fx-font-size: 18px;\r\n" + "	-fx-font-weight: bold;");
			if (!isDiscountInPrice())
				totalPriceHBox.getChildren().add(totalDiscountPriceLabel);
		} else {
			if (isDiscountInPrice())
				totalPriceHBox.getChildren().remove(totalDiscountPriceLabel);
		}
	}

	/**
	 * This method calculates the total price of products/items that selected in the
	 * overview.
	 * 
	 * @return totalPrice without discounts.
	 */
	private double getTotalPriceFromOverview() {
		double totalPrice = 0;
		for (Node currentSelected : overViewVBox.getChildren()) {
			if (currentSelected instanceof SelectedHBox) {
				totalPrice += (((SelectedHBox) currentSelected).getQuantity())
						* (((SelectedHBox) currentSelected).getProduct().getPrice());
			}
		}
		return totalPrice;
	}

	/**
	 * This method calculate the total price by selecting products/items with
	 * discount to the total price.
	 * 
	 * @return the totalDiscount by selecting products/items with discount.
	 */
	private double getTotalDiscountPriceFromOverview() {
		double totalDiscountPrice = 0;
		for (Node currentSelected : overViewVBox.getChildren()) {
			if (currentSelected instanceof SelectedHBox) {
				totalDiscountPrice += (((SelectedHBox) currentSelected).getQuantity())
						* (((SelectedHBox) currentSelected).getProduct().calculateDiscount());
			}
		}
		return totalDiscountPrice;
	}

	/**
	 * This method checking if the selected items/products have discount.
	 * 
	 * @return true or false depeding on the selected products/items.
	 */
	private boolean isDiscount() {
		for (Node node : overViewVBox.getChildren())
			if (node instanceof SelectedHBox) {
				SelectedHBox selectedHBox = (SelectedHBox) node;
				if (selectedHBox.getProduct().isDiscount())
					return true;
			}
		return false;
	}

	/**
	 * Checking if the product/items contains discount.
	 * 
	 * @return true or false
	 */
	private boolean isDiscountInPrice() {
		return totalPriceHBox.getChildren().contains(totalDiscountPriceLabel);
	}
}
