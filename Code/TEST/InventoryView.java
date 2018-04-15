package rim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
public class InventoryView
{	
	public static Scene displayProducts(int sort, Stage stage) {
		List<Product> products = Product.getProducts();
		int count = 1;
		int sortType = sort;

//here above should have a top scene includes sorting, logout, and account	
		if(sortType == 1)
		{
			products = Product.sortName(products);
		}
		else if(sortType == 2)
		{
			products = Product.sortPrice(products);
		}
		else if(sortType == 3)
		{
			products = Product.sortQuantity(products);
		}
		else if(sortType == 4)
		{
			products = Product.sortType(products);
		}
		else if(sortType == 5)
		{
			products = Product.sortSupplier(products);
		}
		
	
		
// table scene		
		TableView<Product> table = new TableView<>();
		for(Product product : products)
		{
			table.getItems().add(product);
			System.out.println(count + ". " + product.getName());
			count++;
		}
		
		 TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
		 nameColumn.setMinWidth(250);
		 nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		 
		 TableColumn<Product, Integer> priceColumn = new TableColumn<>("Price");
		 priceColumn.setMinWidth(100);
		 priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		 
		 TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
		 quantityColumn.setMinWidth(100);
		 quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		 
		BorderPane layout=new BorderPane();
		table.getColumns().add(nameColumn);
		table.getColumns().add(priceColumn);
		table.getColumns().add(quantityColumn);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


//Buttons for cart
		HBox bottom = new HBox();
		bottom.setPadding(new Insets(15, 12, 15, 12));
		bottom.setSpacing(10);
		bottom.setStyle("-fx-background-color: #808080;");
	    
		Button add = new Button();
		add.setText("Add to Cart");
		add.setPrefSize(100, 30);
		add.setOnAction( new EventHandler<ActionEvent>(){
    		@Override public void handle(ActionEvent e) {
    			
		        ObservableList<Product> selectedItem = table.getSelectionModel().getSelectedItems();		  
		        for (Product p : selectedItem) {
		           Cart.addToCart(p, 1);
		        }

		    }
		});
		
		Button cart= new Button();
		cart.setText("Your Cart");
		cart.setPrefSize(100, 30);
		cart.setOnAction(e -> CartView.DisplayCart(stage));
		
		bottom.getChildren().addAll(add, cart);
		bottom.setAlignment(Pos.BASELINE_RIGHT);
		
//add individual scene into layout so they can be in the same scene in different area	
		layout.setCenter(table);
		layout.setBottom(bottom);
		return new Scene(layout,500,500);
//		while(true)
//		{
//			System.out.print("\nEnter product # or command: ");
//			Scanner s = new Scanner(System.in);
//			String command = s.nextLine();
//			//System.out.println(command.split(" ").length == 7);
//
//			if(command.matches("\\d+") && Integer.parseInt(command) > 0 && Integer.parseInt(command) <= products.size())
//			{
//				int productNum = Integer.parseInt(command) -1;
//				DetailsView.displayDetails(products.get(productNum));
//			}
//			else if(command.contains("add") && command.split(" ").length == 8 && command.split(" ")[4].matches("\\d+") && command.split(" ")[5].matches("\\d+") && command.split(" ")[6].matches("\\d+\\.\\d+"))
//			{
//				if(Account.getCurrentUser().getManagerId() == null){
//					System.out.println("You do not have permission to add products. Only Managers can do so.");
//					continue;
//				}
//				else{
//
//					String name= command.split(" ")[1];
//					String type= command.split(" ")[2];
//					String description= command.split(" ")[3];
//					int quantity= Integer.parseInt(command.split(" ")[4]);
//					int threshold= Integer.parseInt(command.split(" ")[5]);
//					double price= Double.parseDouble(command.split(" ")[6]);
//					String supplier= command.split(" ")[7];
//					Product.addProduct(name, type, description, quantity, threshold, price, supplier);
//				}
//			}
//			else if(command.contains("remove") && command.split(" ").length == 2 && command.split(" ")[1].matches("\\d+")&& Integer.parseInt(command.split(" ")[1]) > 0 && Integer.parseInt(command.split(" ")[1]) <= products.size())
//			{
//				if(Account.getCurrentUser().getManagerId() == null){
//					System.out.println("You do not have permission to remove products. Only Managers can do so.");
//					continue;
//				}
//				else{
//					System.out.print("\nYou are about to remove an item from the inventory. Are you sure?: ");
//					String prompt = s.nextLine();
//					if(prompt.equals("yes"))
//					{
//
//						String temp = command.split(" ")[1];
//						int productNum = Integer.parseInt(temp) -1;
//						Product.removeProduct(products.get(productNum));
//						refreshDisplayProducts(sortType);
//					}
//				}
//			}
//			else if(command.contains("sort") && command.split(" ").length == 2)
//			{
//				String sortInput = command.split(" ")[1];
//				if(sortInput.equals("name"))
//				{
//					sortType = 1;
//					refreshDisplayProducts(sortType);
//				}
//				else if(sortInput.equals("price"))
//				{
//					sortType = 2;
//					refreshDisplayProducts(sortType);}
//				else if(sortInput.equals("quantity"))
//				{
//					sortType = 3;
//					refreshDisplayProducts(sortType);}
//				else if(sortInput.equals("type"))
//				{
//					sortType = 4;
//					refreshDisplayProducts(sortType);}
//				else if(sortInput.equals("supplier"))
//				{
//					sortType = 5;
//					refreshDisplayProducts(sortType);}
//				else
//					System.out.println("Invalid Command.");
//
//			}
//			else if(command.equals("threshold"))
//			{
//				ThresholdView.displayThresholdProduct(sortType);
//			}
//			else if(command.equals("refresh"))
//			{
//				refreshDisplayProducts(sortType);
//			}
//			else if(command.equals("cart")){
//				CartView.DisplayCart();
//			}
//			else if(command.equals("account"))
//			{
//				System.out.println(Account.getCurrentUser().getManagerId());
//				AccountView.accountView();
//			}
//			else if(command.contentEquals("logout"))
//			{
//				System.out.println("Logging Out...\n");
//				LoginView.login(stage);
//			}
//			else if(command.contentEquals("quit"))
//			{
//				System.out.println("Quitting...\n");
//				System.exit(1);;
//			}
//			else if (command.contains("stats") || command.contains("statistics"))
//			{
//				String [] temp = command.split(" ");
//				if(temp.length == 1) {
//					InventoryStatistics invs = new InventoryStatistics(products);
//					invs.printStatistics();
//				} else {
//					InventoryStatistics invs = new InventoryStatistics(products, temp[1]);
//					invs.printStatistics();
//				}
//				System.out.println();
//				
//			} 
//			else
//			{
//				System.out.println("Invalid Command.");
//			}
//		}
		
	}

	public static void refreshDisplayProducts(int sort) {
		displayProducts(sort);

	}

}