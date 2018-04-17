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

	List<Employee> employees = Employee.getEmployees();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for(Employee e : employees) {
			employeeListView.getItems().add(e.getFirstName()+", "+e.getLastName());
		}
		employeeListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		accountText.setText(Account.accountInfo(Account.getCurrentUser()));
	}
	public void backButtonPressed(ActionEvent event) throws IOException{
		Parent inventoryViewParent = FXMLLoader.load(getClass().getResource("InventoryView.fxml"));
		Scene inventoryViewScene = new Scene(inventoryViewParent,1200, 800);
		
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(inventoryViewScene);
		window.show();
	}
	public void inventoryButtonPressed(ActionEvent event) throws IOException{
		Parent inventoryViewParent = FXMLLoader.load(getClass().getResource("InventoryView.fxml"));
		Scene inventoryViewScene = new Scene(inventoryViewParent,1200, 800);
		
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(inventoryViewScene);
		window.show();
	}
	public void logOutPressed(ActionEvent event) throws IOException {
		Parent loginViewParent = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
		Scene loginViewScene = new Scene(loginViewParent);
		
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(loginViewScene);
		window.show();
	}
	public void detailPressed() {
		Employee e = findEmployee((String) employeeListView.getSelectionModel().getSelectedItem());
		this.employeeLabel.setText("Employee: " + e.getFirstName() + " "+e.getLastName());
		this.employeeText.setText(Account.accountInfo(e));
	}
	
	public Employee findEmployee(String username) {
		for(Employee e:employees) {
			if((e.getFirstName()+", "+e.getLastName()).equals(username)) {
				return e;
			}
		}
		return null;
	}
	public void addEmployeePressed() {
		
	}
	public void editEmployeePressed() {
		
	}
	public void deleteEmployeePressed() {
		
	}
	public void changePassPressed() {
		
		
	}
}
