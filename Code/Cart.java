package rim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Cart {
static List<CartItem> cart = new ArrayList<CartItem>();
	
	public static void addToCart(Product p, int quantity){
		int SellingQuantity = quantity;
		boolean same= false;
		CartItem item= new CartItem(p, SellingQuantity);
		for(CartItem x : cart){
			if(x.getP()==p){
				x.setSellingQuantity(x.getSellingQuantity()+quantity);
				same=true;
			}
		}
		if(!same){
			cart.add(item);
		}
	}
	
	public static void deleteItem(int index){
		try{
		    cart.remove(index-1);
		}catch(IndexOutOfBoundsException exception){
			System.out.println("No such item rank in your cart");
		}
		
	}
	
	public static void clearCart(){
		cart.clear();
	}
	public static void editItem(int index, int quantity){
		try{
			cart.get(index-1).setSellingQuantity(quantity);
		}
		catch(IndexOutOfBoundsException exception){
			System.out.println("No such item rank in your cart");
		}
	}
	
	public static List<CartItem> getCart(){
		return cart;
	}
	
	public static boolean checkOut(){
		
		double totalSale=0;
		double salesTax;
		if(cart.isEmpty()){
			System.out.println("Your cart is empty");
			return false;
		}
		else{
		for (CartItem p : cart) {
			totalSale+=purchaseProduct(p);
		}
		
		salesTax= totalSale * .07;
		System.out.printf("Tax: %36s%.2f \nTotal: %34s%.2f \n","$",salesTax,"$",totalSale+salesTax);
		cart.clear();
		return true;
		}	
	}
	
	public static double purchaseProduct(CartItem p) 
	{
	
		String date = Date.getDate();
		String productId = p.getP().getId();
		int quantitySold = p.getSellingQuantity();
		double totalSale = p.getP().getPrice() * quantitySold;
		double salesTax = totalSale * .07;
		String employeeId = Account.getCurrentUser().getId();
		
		insertSaleEntry(date, productId, quantitySold, totalSale, salesTax, employeeId);
		Product.updateQuantity(productId, -quantitySold);
			
		System.out.print(p);
		return totalSale;
	}

	public static void insertSaleEntry(String date, String productId, int quantitySold, double totalSale, double salesTax, String employeeId){

		Connection conn = ConnectionFactory.makeConnection();

		String query = "INSERT INTO Sales (the_date, product_id, quantity_sold, total_sale, sale_tax, employee_id)" +
				" 		VALUES ('" + date + "', '" + productId + "','" + quantitySold + "','" + totalSale + "', '" + salesTax + "','" + employeeId + "')";
		
		//System.out.println(query);


		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		ConnectionFactory.closeConnection(conn, ps, rs);
	}
}
