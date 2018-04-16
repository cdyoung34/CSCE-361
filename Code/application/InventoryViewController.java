package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import rim.Cart;
import rim.Product;

public class InventoryViewController implements Initializable {
	
	@FXML private ListView listView;
	@FXML private TextArea textArea;
	@FXML private Label detailsLabel;
	@FXML private ListView cartList;
	@FXML private Label cartLabel;
	private double priceSum = 0;
	// list of items that were checked out, this is called in the ReceiptController
	private List<Product> checkoutCart = new ArrayList<Product>();
	public List<Product> getCheckoutCart() {
		return checkoutCart;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Product> products = Product.getProducts();
		for(Product p : products) {
			listView.getItems().add(p.getName());
		}
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		cartList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	/**
	 * display details in textArea
	 */
	public void detailsButtonPressed() {
		Product p = findProduct((String) listView.getSelectionModel().getSelectedItem());
		this.detailsLabel.setText("Item: " + p.getName());
		this.textArea.setText(p.formatItemDetails());
	}
	
	
	public void addToCartButtonPressed() {
		Product p = findProduct((String) listView.getSelectionModel().getSelectedItem());
		// add to diplay list
		cartList.getItems().add(p.getName() + " - $" + String.format("%.2f", p.getPrice()));
		this.priceSum += p.getPrice();
		this.cartLabel.setText(String.format("Cart Total: $%.2f", priceSum));
	}
	
	public void removeFromCartButtonPressed() {
		Product p = findProduct((String) listView.getSelectionModel().getSelectedItem());
		cartList.getItems().remove(cartList.getSelectionModel().getSelectedIndex());
		this.priceSum -= p.getPrice();
		this.cartLabel.setText(String.format("Cart Total: $%.2f", priceSum));
	}
	
	public void clearCartButtonPressed() {
		cartList.getItems().clear();
		this.priceSum = 0;
		this.cartLabel.setText(String.format("Cart Total: $%.2f", priceSum));
	}
	
	public void checkOutButtonPressed(ActionEvent event) throws IOException {
		// add check out items to list
		ObservableList os = cartList.getItems();
		for(int i = 0; i < os.size(); i++) {
			String temp = (String) os.get(i);
			temp = temp.substring(0, temp.indexOf("-") - 1);
			Cart.addToCart(findProduct(temp), 1);
			checkoutCart.add(findProduct(temp));
		}
		Parent loginViewParent = FXMLLoader.load(getClass().getResource("Receipt.fxml"));
		Scene loginViewScene = new Scene(loginViewParent);
		
		// get the stage information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(loginViewScene);
		window.show();
		
	}
	
	public Product findProduct(String productName) {
		List<Product> products = Product.getProducts();
		for (Product p : products) {
			if (p.getName().equals(productName)) {
				return p;
			}
		}
		return null;
	}
	// configure list area
	
	/**
	 * return to login page
	 */
	public void returnToLogin(ActionEvent event) throws IOException{
		Parent loginViewParent = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
		Scene loginViewScene = new Scene(loginViewParent);
		
		// get the stage information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(loginViewScene);
		window.show();
		
	}
	
	public void toAccount(ActionEvent event) throws IOException{
		Parent managerViewParent = FXMLLoader.load(getClass().getResource("managerView.fxml"));
		Scene managerScene = new Scene(managerViewParent);
		
		// get the stage information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(managerScene);
		window.show();
	}
	public void toStatistic(ActionEvent event) throws IOException{
		
	}
	
	/**
	 * go to edit view
	 */
	public void editModePressed(ActionEvent event) throws IOException{
		Parent editViewParent = FXMLLoader.load(getClass().getResource("EditView.fxml"));
		Scene editViewScene = new Scene(editViewParent,1200, 800);
		
		// get the stage information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(editViewScene);
		window.show();
	}
	
}
