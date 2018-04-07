package rim;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class InventoryView 
{	
	public static void displayProducts(int sort) {
		List<Product> products = Product.getProducts();
		int count = 1;
		int sortType = sort;

		System.out.print("******************************************************************************\n"
				+ "        Inventory Product Overview\n"
				+ "******************************************************************************\n");
		
		if(sortType == 1)
		{
			products = Product.sortName(products);
		}

		for(Product product : products)
		{
			System.out.println(count + ". " + product.getName());
			count++;
		}

		while(true)
		{
			System.out.print("\nEnter product # or command: ");
			Scanner s = new Scanner(System.in);
			String command = s.nextLine();
			//System.out.println(command.split(" ").length == 7);

			if(command.matches("\\d+") && Integer.parseInt(command) > 0 && Integer.parseInt(command) <= products.size())
			{
				int productNum = Integer.parseInt(command) -1;
				DetailsView.displayDetails(products.get(productNum));
			}
			else if(command.contains("add") && command.split(" ").length == 8 && command.split(" ")[4].matches("\\d+") && command.split(" ")[5].matches("\\d+") && command.split(" ")[6].matches("\\d+\\.\\d+"))
			{
				if(Account.getCurrentUser().getManagerId() == null){
					System.out.println("You do not have permission to add products. Only Managers can do so.");
					continue;
				}
				else{

					String name= command.split(" ")[1];
					String type= command.split(" ")[2];
					String description= command.split(" ")[3];
					int quantity= Integer.parseInt(command.split(" ")[4]);
					int threshold= Integer.parseInt(command.split(" ")[5]);
					double price= Double.parseDouble(command.split(" ")[6]);
					String supplier= command.split(" ")[7];
					Product.addProduct(name, type, description, quantity, threshold, price, supplier);
				}
			}
			else if(command.contains("remove") && command.split(" ").length == 2 && command.split(" ")[1].matches("\\d+")&& Integer.parseInt(command.split(" ")[1]) > 0 && Integer.parseInt(command.split(" ")[1]) <= products.size())
			{
				if(Account.getCurrentUser().getManagerId() == null){
					System.out.println("You do not have permission to remove products. Only Managers can do so.");
					continue;
				}
				else{
					System.out.print("\nYou are about to remove an item from the inventory. Are you sure?: ");
					String prompt = s.nextLine();
					if(prompt.equals("yes"))
					{

						String temp = command.split(" ")[1];
						int productNum = Integer.parseInt(temp) -1;
						Product.removeProduct(products.get(productNum));
						refreshDisplayProducts(sortType);
					}
				}
			}
			else if(command.equals("sort"))
			{
				if(sortType == 0)
				sortType = 1; 
				else
					sortType = 0; 
				refreshDisplayProducts(sortType);
			}
			else if(command.equals("refresh"))
			{
				refreshDisplayProducts(sortType);
			}
			else if(command.equals("cart")){
				CartView.DisplayCart();
			}
			else if(command.equals("account"))
			{
				System.out.println(Account.getCurrentUser().getManagerId());
				AccountView.accountView();
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

	public static void refreshDisplayProducts(int sort) {
		displayProducts(sort);

	}

}

