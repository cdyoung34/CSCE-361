package rim;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public abstract class Account {

	String id;
	String firstName;
	String lastName;
	String userName;

	public Account(String id, String firstName, String lastName, String userName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
	}

	public abstract String getId();

	public abstract String getFirstName();

	public abstract String getLastName();

	public abstract String getUserName();


	//method to create a person
	public static List<Account> getAccounts(){

		List<Account> accounts = new ArrayList<Account>();

		accounts.addAll(Employee.getEmployees());
		accounts.addAll(Manager.getManagers());

		return accounts;
	}

}