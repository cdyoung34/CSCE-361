package rim;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Manager extends Account{

	String id;
	String firstName;
	String lastName;
	String userName;
	String managerId;

	public Manager(String id, String firstName, String lastName, String userName, String managerId) {
		super(id, firstName, lastName, userName);
		this.managerId = managerId;
	}

	public Manager(Manager m) {
		super(m.getId(), m.getFirstName(), m.getLastName(), m.getUserName());
		this.managerId = m.getManagerId();

	}

	//method to create a manager
	public static List<Manager> getManagers(){

		Connection conn = ConnectionFactory.makeConnection();

		String query = "SELECT e.id AS id, " +
				" 		e.first_name AS firstName, " +
				"       e.last_name AS lastName, " + 
				"       e.user_name AS userName, " +
				"       e.manager_id AS managerId " + 
				"FROM Employees e WHERE manager_id is NOT NULL";


		List<Manager> managers = new ArrayList<Manager>();

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

				Manager m = new Manager(id, firstName, lastName, userName, managerId);
				managers.add(m);
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		ConnectionFactory.closeConnection(conn, ps, rs);
		return managers;
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