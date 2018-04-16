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
import rim.Account;
import rim.Cart;
import rim.Employee;
import rim.Product;
public class managerViewController implements Initializable{
	@FXML private ListView employeeListView;
	@FXML private TextArea accountText;
	@FXML private Label employeeLabel;
	@FXML private TextArea employeeText;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Employee> employees = Employee.getEmployees();
		for(Employee e : employees) {
			employeeListView.getItems().add(e.getFirstName()+", "+e.getLastName());
		}
		employeeListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		accountText.setText(Account.getCurrentUser().toString());
	}
	public void backButtonPressed(){
		
	}
	public void inventoryButtonPressed(ActionEvent event) throws IOException {
		
		Parent inventoryViewParent = FXMLLoader.load(getClass().getResource("InventoryView.fxml"));
		Scene inventoryViewScene = new Scene(inventoryViewParent,1200, 800);
		
		// get the stage information
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(inventoryViewScene);
		window.show();
		
	}
	public void logOutPressed() {
		
	}
	public void detailPressed() {
		
	}
	public void addEmployeePressed() {
		
	}
	public void deleteEmployeePressed() {
		
	}
	public void changePassPressed() {
		
	}
}
