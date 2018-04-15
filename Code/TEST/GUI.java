package rim;


import javafx.scene.control.Label;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
 
public class GUI extends Application {
	Stage window;
	Scene scene1;
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
//        primaryStage.setTitle("Inventory mangement system");
//        Label label = new Label("Welcome to the Inventory mangement system");
//        
//        Button btn = new Button();
//        btn.setText("LOG IN");
//        btn.setOnAction(e -> primaryStage.setScene(new LoginView(LoginView.login())));      
//        
//        VBox root = new VBox(40);
//        root.setAlignment(Pos.CENTER);
//        root.getChildren().addAll(label, btn);
        primaryStage.setScene(LoginView.login(primaryStage));
        primaryStage.show();
    }
  }