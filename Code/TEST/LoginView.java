package rim;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.scene.control.Label;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class LoginView{
	final static Label message = new Label("");
	static String pass=("");
	static String name=("");
	
	public static Scene login(Stage primaryStage){
        primaryStage.setTitle("Inventory mangement system");
        Label label = new Label("Welcome to the Inventory mangement system");
        
        Button btn = new Button();
        btn.setText("LOG IN");
        btn.setOnAction(e -> primaryStage.setScene(login1(primaryStage)));      
        
        VBox login = new VBox(40);
        login.setAlignment(Pos.CENTER);
        login.getChildren().addAll(label, btn);
        
        return new Scene(login,400,200);
	}
	
	public static Scene login1(Stage stage){
		 GridPane login = new GridPane();
		 login.setPrefSize(400, 200);
		 login.setHgap(10);
		 login.setVgap(10);

		    HBox hbButtons = new HBox(10);
		    hbButtons.setSpacing(10.0);

		    Button Submit = new Button("Submit");
		    Button Clear = new Button("Clear");
		    Button Exit = new Button("Exit");

		    Label lblName = new Label("User name:");
		    TextField tfName = new TextField();
		    Label lblPwd = new Label("Password:");
		    PasswordField pfPwd = new PasswordField();

		    hbButtons.getChildren().addAll(Submit, Clear, Exit);
		    login.add(lblName, 0, 0);
		    login.add(tfName, 1, 0);
		    login.add(lblPwd, 0, 1);
		    login.add(pfPwd, 1, 1);
		    login.add(message, 1, 2);
		    login.add(hbButtons, 0, 3, 2, 1);
		
		   
		    Submit.setOnAction( new EventHandler<ActionEvent>(){
		    		@Override public void handle(ActionEvent e) {
		    			name=tfName.getText();
		    			pass=pfPwd.getText();
		    			if(!checkPassword(name, pass))
		    				{
		    				 	message.setText("Your password is incorrect!");
		    		            message.setTextFill(Color.rgb(210, 39, 30));
		    		         
		    				}
		    				
		    			else{
		    				message.setText("");
		    				Account.setCurrentUser(Account.accountFromUsername(tfName.getText()));
		    				stage.setScene(InventoryView.displayProducts(1, stage));
//		    				InventoryView.displayProducts(0);
		    				
		    			}
		    		}
		  });
		  Clear.setOnAction(e -> tfName.clear());
		  Clear.setOnAction(e -> pfPwd.clear());
		  Exit.setOnAction(e -> stage.setScene(login(stage)));
		    login.setAlignment(Pos.CENTER);
		    return new Scene(login);
	}

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


