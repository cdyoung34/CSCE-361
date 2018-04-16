package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rim.Account;
import rim.ConnectionFactory;




public class LoginController implements Initializable {
	
	@FXML private Label credentials;
	@FXML private TextField username;
	@FXML private PasswordField password;
	
	public void loginButtonPushed(ActionEvent event) throws IOException {
		// if valid credentials, go to InventoryView page
		if (checkPassword(username.getText(), password.getText())) {
			Account.setCurrentUser(Account.accountFromUsername(username.getText()));
			Parent inventoryViewParent = FXMLLoader.load(getClass().getResource("InventoryView.fxml"));
			Scene inventoryViewScene = new Scene(inventoryViewParent,1200, 800);
			
			// get the stage information
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(inventoryViewScene);
			window.show();
		} else {
			credentials.setText("Invalid credentials!");
		}
	}
		
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		credentials.setText("");
		
		
	}
	
	
	/**
	 * verify the credentials
	 */
	private static boolean checkPassword(String username, String password) {

		String input = password;
		String dataPassword = null;
		Connection conn = ConnectionFactory.makeConnection();

		String query = "SELECT e.id AS id, " +
				" 		e.first_name AS firstName, " +
				"       e.last_name AS lastName, " + 
				"       e.user_name AS userName, " +
				"       e.password AS password " +
				"FROM Employees e WHERE user_name = '" + username + "'";

		
		//System.out.println(query);


		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				dataPassword       = rs.getString("password");
				//System.out.println(dataPassword);

			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		ConnectionFactory.closeConnection(conn, ps, rs);
		if(dataPassword == null)
		{
			return false;
		}
		else if(dataPassword.equals(password))
		{
			return true;
		}
		else
		{
			return false;
		}

	}
}
