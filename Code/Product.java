import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Product {
	
	Credentials cred = new Credentials();
	
	List<Integer> itemsSoldPerDay = new ArrayList<Integer>();
	int inStockCount; 
	String productCode;
	
	public Product(List<Integer> itemsSoldPerDay, int inStockCount, String productCode) {
		//super();
		this.itemsSoldPerDay = itemsSoldPerDay;
		this.inStockCount = inStockCount;
		this.productCode = productCode;
	}
	
	public Product( ) {
		//super();
		this.itemsSoldPerDay = itemsSoldPerDay;
		this.inStockCount = inStockCount;
		this.productCode = productCode;
	}
	
	private void  getItemsSold(String date){

		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try {
			try {
				Class.forName(cred.driver).newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Cannot load Driver ");
		}
		try {
			conn = DriverManager.getConnection(cred.url, cred.userName, cred.password);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		//itemSoldPerDay
		 String query = " select * from  Sales where date = ?  ";    
		 
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, date);
			rs = ps.executeQuery();
			System.out.print("id\t\t date\t\t sum_total \t quantity\n");
			while(rs.next()){
				System.out.printf("%d \t\t %s \t %d \t\t %d\n", rs.getInt("id"), rs.getString("date"), rs.getInt("sum_total"), rs.getInt("quantity")  );
			}
			System.out.println();
		} catch (SQLException e) {
			System.out.println("SQLException: ...");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
				
		try {
			if(rs != null && !rs.isClosed()){
				rs.close();
			}
			if(ps != null && !ps.isClosed()){
				ps.close();
			}
			if(conn != null && conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("SQLException: cannot close");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		//return itemsSold;
	}
	
	
}
