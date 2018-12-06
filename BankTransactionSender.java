import java.sql.*;

public class BankTransactionSender{	
	private static DBConnection dbc=DBConnection.getInstance();
	public static void enter_check(int account, double amount, long date) throws Exception{
		ResultSet rs=dbc.sendQuery("SELECT * FROM Account WHERE aid="+account);
		rs.next();
		System.out.println("hi "+rs.getString("type")+" "+rs.getDouble("balance")+" "+rs.getBoolean("closed"));
		System.out.println(amount);
		Account a=new Account(account,rs.getString("type"),rs.getDouble("balance"),rs.getBoolean("closed"));
		TransactionSender.write_check(a, amount, date);
	}
	public static ResultSet monthly_statement(User user) throws Exception{
		return null;
	}
	public static ResultSet list_closed_accounts() throws Exception{
		return null;
	}
	public static ResultSet large_transactions_report() throws Exception{
		return null;
	}
	public static ResultSet customer_report(User user) throws Exception{
		return dbc.sendQuery("SELECT A.aid AS 'aid', A.type AS 'type', A.balance AS 'balance', A.closed AS 'closed' FROM"+
		" (SELECT * FROM Owner WHERE pin="+user.pin+" INNER NATURAL JOIN Account A");
	}
	public static void add_interest() throws Exception{

	}
	public static void create_account(String type, int balance, String[]owners) throws Exception{

	}
	public static void delete_closed_accounts_and_customers() throws Exception{
		dbc.sendQuery("DELETE FROM Account WHERE closed=1");
		dbc.sendQuery("DELETE FROM Customer C WHERE COUNT(Select * FROM OWNER O WHERE C.pin=O.pin)=0");
	}
	public static void delete_transactions() throws Exception{
		dbc.sendQuery("DELETE FROM Transactions");
	}
}