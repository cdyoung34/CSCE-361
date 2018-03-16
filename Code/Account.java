package rim;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Account {

	String id;
	String firstName;
	String lastName;
	String userName;
	String managerId;

	public Account(String id, String firstName, String lastName, String userName, String managerId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.managerId = managerId;
	}

	public Account(Account a) {
		this.id = a.getId();
		this.firstName = a.getFirstName();
		this.lastName = a.getLastName();
		this.userName = a.getUserName();
		this.managerId = a.getManagerId();

	}

	//method to create a person
	public static List<Account> getEmployees(){

		Connection conn = ConnectionFactory.makeConnection();

		String query = "SELECT e.id AS id, " +
				" 		e.first_name AS firstName, " +
				"       e.last_name AS lastName, " + 
				"       e.user_name AS userName, " +
				"       e.manager_id AS managerId " + 
				"FROM Employees e WHERE manager_id is NOT NULL";


		List<Account> accounts = new ArrayList<Account>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				String id       = rs.getString("id");
				String firstName       = rs.getString("first_name");
				String lastName   = rs.getString("last_name");
				String userName = rs.getString("user_name");
				String managerId = rs.getString("manager_id");

				Account a = new Account(id, firstName, lastName, userName, managerId);
				accounts.add(a);
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		ConnectionFactory.closeConnection(conn, ps, rs);
		return accounts;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

}