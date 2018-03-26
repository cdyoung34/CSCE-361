package rim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cart {
	
	public static void purchaseProduct(Product p, int quantity) 
	{
	
		String date = Date.getDate();
		String productId = p.getId();
		int quantitySold = quantity;
		double totalSale = p.getPrice() * quantitySold;
		double salesTax = totalSale * .07;
		String employeeId = Account.getCurrentUser().getId();
		
		insertSaleEntry(date, productId, quantitySold, totalSale, salesTax, employeeId);
		Product.updateQuantity(productId, -quantitySold);
			
		System.out.printf("\"%s\" x%d purchased for $%.2f plus $%.2f.\n", p.getName(), quantitySold, totalSale, salesTax);
		System.out.printf("Total: $%.2f\n", totalSale + salesTax);
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
