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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import rim.Cart;
import rim.Product;

public class StatisticsViewController implements Initializable {
	@FXML private ListView listView;
	@FXML private ComboBox comboBox;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Product> products = Product.getProducts();
		for(Product p : products) {
			listView.getItems().add(p.getName());
		}
		
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		// for combo box
		this.initializeMonths();
	}
	
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
	
	private void initializeMonths() {
		comboBox.getItems().add("January");
		comboBox.getItems().add("February");
		comboBox.getItems().add("March");
		comboBox.getItems().add("April");
		comboBox.getItems().add("May");
		comboBox.getItems().add("June");
		comboBox.getItems().add("July");
		comboBox.getItems().add("August");
		comboBox.getItems().add("September");
		comboBox.getItems().add("October");
		comboBox.getItems().add("November");
		comboBox.getItems().add("December");
	}

}
