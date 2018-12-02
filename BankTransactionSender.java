import java.sql.*;

public class BankTransactionSender{	
	private static DBConnection dbc=DBConnection.getInstance();
	public void enter_check(Account account, double amount, long date) throws Exception{
		TransactionSender.write_check(account, amount, date);
	}
	public ResultSet monthly_statement(User user) throws Exception{
		return null;
	}
	public ResultSet list_closed_accounts() throws Exception{
		return null;
	}
	public ResultSet large_transactions_report() throws Exception{
		return null;
	}
	public ResultSet customer_report(User user) throws Exception{
		return dbc.sendQuery("SELECT A.aid AS 'aid', A.type AS 'type', A.balance AS 'balance', A.closed AS 'closed' FROM"+
		" (SELECT * FROM Owner WHERE pin="+user.pin+" INNER NATURAL JOIN Account A");
	}
	public void add_interest() throws Exception{

	}
	public void create_account(String type, int balance, String[]owners) throws Exception{

	}
	public void delete_closed_accounts_and_customers() throws Exception{
		dbc.sendQuery("DELETE FROM Account WHERE closed=1");
		dbc.sendQuery("DELETE FROM Customer C WHERE COUNT(Select * FROM OWNER O WHERE C.pin=O.pin)=0");
	}
	public void delete_transactions() throws Exception{
		dbc.sendQuery("DELETE FROM Transactions");
	}
}