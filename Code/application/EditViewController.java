package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rim.Cart;
import rim.Product;

public class EditViewController implements Initializable{

	
	@FXML private ListView listView;
	@FXML private TextField productName;
	@FXML private TextField type;
	@FXML private TextField supplier;
	@FXML private TextField price;
	@FXML private TextField quantity;
	@FXML private TextField threshold;
	@FXML private TextField description;
	@FXML private Label label;
	private Boolean updateMode = false;
	private Boolean addMode = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Product> products = Product.getProducts();
		for(Product p : products) {
			listView.getItems().add(p.getName());
		}
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
	}
	
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
	
	/**
	 * remove item from inventory
	 */
	public void removeButtonPressed() {
		Product p = findProduct((String) listView.getSelectionModel().getSelectedItem());
		listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
		Product.removeProduct(p);
	}

	public void newButtonPressed() {
		this.clearTextFields();
		this.addMode = true;
		this.updateMode = false;
		label.setText("Create New Item");
	}
	
	/**
	 * Save edits made, whether it be adding or updating an item
	 */
	public void saveButtonPressed() {
		String pName = productName.getText();
		String pType = type.getText();
		String pSup = supplier.getText();
		double pPrice = Double.parseDouble(price.getText());
		int pQuan = Integer.parseInt(quantity.getText());
		int pThre = Integer.parseInt(threshold.getText());
		String pDes = description.getText();
		if (this.addMode) {
			Product.addProduct(pName,pType,pDes,pQuan,pThre,pPrice,pSup);
			listView.getItems().add(pName);
		} else if (this.updateMode) {
			// TODO: add functionality for updating an item
		}
		
		this.clearTextFields();
		label.setText("Update Inventory");
	}
	
//	public void addButtonPressed() {
//		String pName = productName.getText();
//		String pType = type.getText();
//		String pSup = supplier.getText();
//		double pPrice = Double.parseDouble(price.getText());
//		int pQuan = Integer.parseInt(quantity.getText());
//		int pThre = Integer.parseInt(threshold.getText());
//		String pDes = description.getText();
//		
//		Product.addProduct(pName,pType,pDes,pQuan,pThre,pPrice,pSup);
//		listView.getItems().add(pName);
//		
//		this.clearTextFields();
//	}
//	
	public void returnToInventory(ActionEvent event) throws IOException {
		Parent inventoryViewParent = FXMLLoader.load(getClass().getResource("InventoryView.fxml"));
		Scene inventoryViewScene = new Scene(inventoryViewParent,1200, 800);
		
		// get the stage information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(inventoryViewScene);
		window.show();
		Cart.clearCart();
	}
	
	/**
	 * Update Item Button, this method sets the mode but does not actually update the item
	 */
	public void updateButtonPressed() {
		label.setText("Update Item: " + listView.getSelectionModel().getSelectedItem());
		
		Product p = findProduct((String) listView.getSelectionModel().getSelectedItem());
		productName.setText(p.getName());
		type.setText(p.getType());
		supplier.setText(p.getSupplier());
		quantity.setText("" + p.getQuantity());
		price.setText("" + p.getPrice());
		description.setText(p.getDescription());
		threshold.setText("" + p.getType());
		
		this.updateMode = true;
		this.addMode = false;
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
	
	public void clearTextFields() {
		productName.setText("");
		type.setText("");
		supplier.setText("");
		price.setText("");
		quantity.setText("");
		threshold.setText("");
		description.setText("");
	}
}
