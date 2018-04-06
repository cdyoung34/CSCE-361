package rim;

import java.util.Scanner;

public class CartView {
	public static void DisplayCart() {

		Scanner s = new Scanner(System.in);
		while (true) {
			System.out.println("\nYour Cart: ");
			System.out.println(Cart.getCart().toString().replace("[", "").replace("]", "").replace(", ", ""));
			System.out.print("\nEnter a command: ");
			String command = s.nextLine();

			if (command.equals("back") || command.equals("inventory")) {
				InventoryView.displayProducts();
			} else if (command.equals("edit")) {
				System.out.print("Enter the item rank in your cart and the quantity you want change it to: ");
				String edit = s.nextLine();
				Cart.editItem(Integer.parseInt(edit.split(" ")[0]), Integer.parseInt(edit.split(" ")[1]));
			} else if(command.equals("clear")){
				Cart.clearCart();
			} else if (command.contains("delete") && command.split(" ")[1].matches("\\d+")) {
				Cart.deleteItem(Integer.parseInt(command.split(" ")[1]));
			} else if (command.equals("checkout")) {
				Cart.checkOut();
				System.out.println("You have successfully checked out your items in cart\n");
			}
		}
	}
}
