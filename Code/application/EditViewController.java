package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import rim.Cart;
import rim.Product;

public class EditViewController implements Initializable{

	
	@FXML private ListView thresholdList;
	@FXML private TextField productName;
	@FXML private TextField type;
	@FXML private TextField supplier;
	@FXML private TextField price;
	@FXML private TextField quantity;
	@FXML private TextField threshold;
	@FXML private TextArea description;
	@FXML private Label label;
	@FXML private TableView inventoryTable;
	@FXML private TableColumn nameColumn;
	@FXML private TableColumn priceColumn;
	@FXML private TableColumn stockColumn;
	@FXML private TextField searchField;
	private Boolean updateMode = false;
	private Boolean addMode = false;
	
	// for search bar
	ObservableList os = FXCollections.observableArrayList();
	FilteredList filter = new FilteredList(os, e->true);
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.setInventoryTable();
		inventoryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		this.setThresholdList();
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		stockColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		
		
		
	}
	
	private void setInventoryTable() {
		List<Product> products = Product.getProducts();
		for(Product p : products) {
			inventoryTable.getItems().add(p);
			os.add((Product) p);
		}
	}
	
	/**
	 * filters search bar
	 * @param event
	 */
	public void search(KeyEvent event) {
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filter.setPredicate((Predicate<Product>) (Product product)->{
			
			if (newValue.isEmpty() || newValue == null) {
				return true;
			} else if (product.getName().toUpperCase().contains(newValue.toUpperCase())) {
				return true;
			}
			return false;
			
			});
		});
		
		SortedList sort = new SortedList(filter);
		sort.comparatorProperty().bind(inventoryTable.comparatorProperty());
		inventoryTable.setItems(sort);
	}
	
	/**
	 * return to login page
	 */
	public void returnToLogin(ActionEvent event) throws IOException{
		Parent loginViewParent = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
		Scene loginViewScene = new Scene(loginViewParent, 480, 320);
		
		// get the stage information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(loginViewScene);
		window.show();
		
	}
	
	/**
	 * remove item from inventory
	 */
	public void removeButtonPressed() {
		Product p = (Product) inventoryTable.getSelectionModel().getSelectedItem();
		inventoryTable.getItems().remove(inventoryTable.getSelectionModel().getSelectedIndex());
		Product.removeProduct(p);
		this.clearTextFields();
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
			this.setInventoryTable();
		} else if (this.updateMode) {
			// TODO: finish functionality for updating an item
			Product product = (Product) inventoryTable.getSelectionModel().getSelectedItem();
			Product.updatePrice(product, pPrice);
			Product.updateQuantity(product.getId(), pQuan);
			Product.updateDescription(product, pDes);
			Product.updateName(product, pName);
			Product.updateSupplier(product, pSup);
			Product.updateThreshold(product, pThre);
			Product.updateType(product, pType);
		}
		
		this.thresholdList.getItems().clear();
		this.setThresholdList();		
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
		Scene inventoryViewScene = new Scene(inventoryViewParent, 1000, 600);
		
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
		label.setText("Update Item: " + ((Product) inventoryTable.getSelectionModel().getSelectedItem()).getName());
		
		Product p = (Product) inventoryTable.getSelectionModel().getSelectedItem();
		productName.setText(p.getName());
		type.setText(p.getType());
		supplier.setText(p.getSupplier());
		quantity.setText("" + p.getQuantity());
		price.setText("" + p.getPrice());
		description.setText(p.getDescription());
		threshold.setText("" + p.getThreshold());
		
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
	
	private void setThresholdList() {
		List<Product> thresholdItems = Product.getThresholdProducts();
		for (Product p : thresholdItems) {
			thresholdList.getItems().add(p.getName());
		}
	}
}