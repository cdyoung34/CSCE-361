package rim;

import java.util.Scanner;

public class DetailsView {

	public static void displayDetails(Product p) {

		System.out.print("******************************************************************************\n"
				+ "        Product Details: " + p.getName() +"\n"
				+ "******************************************************************************\n");
		System.out.println("Type: " + p.getType() + "   System Identifier: " + p.getId());
		System.out.println("Price: $" + p.getPrice() + "   Quantity: " + p.getQuantity() + "   Quantity Theshold: " + p.getThreshold());
		System.out.println("Description: " + p.getDescription());
		System.out.println("Supplier: " + p.getSupplier());

		while(true){
			System.out.print("\nEnter a command: ");
			Scanner s = new Scanner(System.in);
			String command = s.nextLine();

			if(command.equals("back") || command.equals("inventory"))
			{
				InventoryView.displayProducts();
			}
			else if(command.equals("account"))
			{
				AccountView.accountView();
			}
			else if(command.contains("buy") && command.split(" ")[1].matches("\\d+"))
			{
				int orderQuantity = Integer.parseInt(command.split(" ")[1]);
				if(orderQuantity > p.getQuantity())
				{
					System.out.println("Insufficient Quantity! No items have been added to the cart.");
					continue;
				}
				else
				{
					Cart.purchaseProduct(p, orderQuantity);
				}

			}
			else if(command.contains("stock") && command.split(" ")[1].matches("\\d+"))
			{
				int stockQuantity = Integer.parseInt(command.split(" ")[1]);
				if(Account.getCurrentUser().getManagerId() == null){
					System.out.println("You do not have permission to stock products. Only Managers can do so.");
					continue;
				}
				else if(stockQuantity <= 0)
				{
					System.out.println("Invalid stock quantity! No items have been stocked in the inventory.");
					continue;
				}
				else
				{
					Product.updateQuantity(p.getId(), stockQuantity);
					System.out.printf("\"%s\" x%d Stocked in the inventory.\n", p.getName(), stockQuantity);
				}

			}
			else if(command.contentEquals("logout"))
			{
				System.out.println("Logging Out...\n");
				LoginView.login();
			}
			else if(command.contentEquals("quit"))
			{
				System.out.println("Quitting...\n");
				System.exit(1);;
			}
			else
			{
				System.out.println("Invalid Command.");
			}
		}
	}
}
