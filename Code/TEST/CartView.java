package rim;

import java.util.List;
import java.util.Scanner;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class CartView {
	public static void DisplayCart(Stage stage) {
		List<CartItem> cart = Cart.getCart();
		TableView<CartItem> table = new TableView<>();
		for(CartItem CartItem : cart)
		{
			table.getItems().add(CartItem);
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
//		Scanner s = new Scanner(System.in);
//		while (true) {
//			System.out.print("******************************************************************************\n"
//					+ "        Your Cart\n"
//					+ "******************************************************************************\n");
//			System.out.println(Cart.getCart().toString().replace("[", "").replace("]", "").replace(", ", ""));
//			System.out.print("\nEnter a command: ");
//			String command = s.nextLine();
//
//			if (command.equals("back") || command.equals("inventory")) {
//				InventoryView.displayProducts(1,stage);
//			} else if (command.equals("edit")) {
//				System.out.print("Enter the item rank in your cart and the quantity you want change it to: ");
//				String edit = s.nextLine();
//				Cart.editItem(Integer.parseInt(edit.split(" ")[0]), Integer.parseInt(edit.split(" ")[1]));
//			} else if(command.equals("clear")){
//				Cart.clearCart();
//			} else if (command.contains("delete") && command.split(" ")[1].matches("\\d+")) {
//				Cart.deleteItem(Integer.parseInt(command.split(" ")[1]));
//			} else if (command.equals("checkout")) {
//				if(Cart.checkOut() == true){
//					System.out.println("You have successfully checked out your items in cart\n");
//				}
//				else{
//					System.out.println("check out fail\n");
//				}
//			}
//			else if(command.equals("refresh"))
//			{
//				DisplayCart(stage);
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
//			else{
//				System.out.println("Invalid command");
//			}
//		}
	}
}
