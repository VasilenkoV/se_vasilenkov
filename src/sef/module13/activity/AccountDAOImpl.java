package sef.module13.activity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

	@SuppressWarnings("unused")
	private Connection conn;

	public AccountDAOImpl(Connection conn) {
		this.conn = conn;
	}

	public List<Account> findAccount(String firstName, String lastName)
			throws AccountDAOException {
		List<Account> accountList = new ArrayList<>();
		try (Statement myStatement = conn.createStatement()){

			ResultSet res = myStatement.executeQuery("SELECT * FROM ACCOUNT");

			while (res.next()){
				int id = res.getInt("ID");
				String first_name = res.getString("FIRST_NAME");
				String last_name = res.getString("LAST_NAME");
				String e_mail = res.getString("E_MAIL");
				Account account = new AccountImpl(id, first_name, last_name, e_mail);
				accountList.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountList;
	}

	public Account findAccount(int id) throws AccountDAOException {
		Account account = null;
		try (Statement myStatement = conn.createStatement()){

			ResultSet res = myStatement.executeQuery("SELECT * FROM ACCOUNT");

			while (res.next()){
				int i = res.getInt("ID");
				if (id == i){
					String first_name = res.getString("FIRST_NAME");
					String last_name = res.getString("LAST_NAME");
					String e_mail = res.getString("E_MAIL");
					account = new AccountImpl(i, first_name, last_name, e_mail);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
	}


	public boolean insertAccount(String firstName, String lastName, String email)
			throws AccountDAOException {
		try (Statement myStatement = conn.createStatement()) {
			myStatement.executeUpdate("INSERT INTO ACCOUNT VALUES (3,'" + firstName + "','" + lastName + "','" + email + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

}
