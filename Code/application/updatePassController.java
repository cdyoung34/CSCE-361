package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import rim.Account;
import rim.ConnectionFactory;
import rim.Employee;
public class updatePassController implements Initializable{
	@FXML private PasswordField passField;
	@FXML private TextField newPassField;
	@FXML private Label credential, newPassLabel;
	
	private String id="";
	private String eventString=null;
	List<Employee> employees = Employee.getEmployees();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		credential.setText("");
	}
	
	public void updateButtonPressed(ActionEvent event) throws IOException {
		if (checkPassword(Account.getCurrentUser().getUserName(), passField.getText())) {
			if(id.equals("")) {
				Account.changePassword(Account.getCurrentUser().getId(), newPassField.getText());
			}else {
				Account.changePassword(id, newPassField.getText());
			}
			credential.setText("Successed!");
		}
		else {
			credential.setText("Invalid credentials!");
		}
	}
//	
	public void setConditionLabel() {
		newPassLabel.setText("Enter employee's new password");
	}
	public void setId(String id) {
		this.id=id;
	}
	private static boolean checkPassword(String username, String password) {

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
