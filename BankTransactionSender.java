import java.sql.*;
import java.util.*;
public class BankTransactionSender{	
	private static DBConnection dbc=DBConnection.getInstance();
	public static void enter_check(int aid, double amount, long date) throws Exception{
		TransactionSender.write_check(TransactionSender.getAccount(aid), amount, date);
	}
	
	public static MonthlyStatementResultSet monthly_statement(String pin,long date) throws Exception{
		User user=TransactionSender.getUser(pin);
		user.isLoggedIn=true;
		MonthlyStatementResultSet msrs=new MonthlyStatementResultSet();
		ResultSet balances=dbc.sendQuery("SELECT balance FROM (SELECT aid FROM Owner WHERE pin="+pin+") NATURAL INNER JOIN (SELECT aid, balance FROM Account) ORDER BY aid");
		try{
			while(balances.next()){
				msrs.balances.add(balances.getDouble("balance"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		ResultSet owners=dbc.sendQuery("SELECT name, address, aid FROM ((SELECT aid FROM Owner WHERE pin="+pin+") NATURAL INNER JOIN (SELECT * FROM Owner)) NATURAL INNER JOIN Customer ORDER BY aid");
		try{
			while(owners.next()){
				OwnerTuple oo=new OwnerTuple();
				oo.name=owners.getString("name");
				oo.address=owners.getString("address");
				oo.aid=owners.getString("aid");
				msrs.owners.add(oo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		msrs.transactions=dbc.sendQuery("SELECT aid, tid, type, aid1, aid2, amount, checkID, T.daysSince1970 FROM (SELECT aid FROM Owner WHERE pin="+pin+") A INNER JOIN (SELECT * FROM Transaction WHERE "+date+
			"-daysSince1970<30) T ON A.aid=T.aid1 OR A.aid=T.aid2 ORDER BY aid, daysSince1970 DESC");
		return msrs;
	}
	public static ResultSet list_closed_accounts() throws Exception{
		return null;
	}
	public static ResultSet large_transactions_report() throws Exception{

		return dbc.sendQuery("SELECT DISTINCT pin, name FROM (Owner NATURAL INNER JOIN"
			+"(SELECT aid FROM (Account A INNER JOIN (SELECT * FROM Transaction WHERE type='deposit' OR type='wire' OR type='transfer') T ON A.aid=T.aid1 OR A.aid=T.aid2) GROUP BY aid HAVING SUM(amount)>10000))"
			+"NATURAL INNER JOIN Customer");
	}
	public static ResultSet customer_report(String pin) throws Exception{
		return dbc.sendQuery("SELECT aid, closed FROM"+
		" (SELECT * FROM Owner WHERE pin="+pin+") O NATURAL INNER JOIN Account A");
	}
	public static void add_interest(int accountID, long date) throws Exception{
		TransactionSender.accrue_interest(TransactionSender.getAccount(accountID),date);
	}
	public static void create_account(int accountID, int linkedID, String type, double balance, ArrayList<String>owners) throws Exception{
		ResultSet rs2=dbc.sendQuery("SELECT aid FROM Account WHERE aid="+accountID);
		try{
			rs2.next();
			rs2.getString("aid");
		}catch(Exception e){
			dbc.sendQuery("INSERT INTO Account (aid, type,balance) " + 
		"VALUES ("+accountID+", '"+type+"',"+balance+")");
			if(type=="Pocket"){
				dbc.sendQuery("INSERT INTO PocketAccount (aid, linked_aid, type)  " + 
		"VALUES ("+accountID+", "+linkedID+", 'Pocket')");
			}
		}
		boolean first=true;
		for(String o:owners){
			ResultSet rs=dbc.sendQuery("SELECT pin FROM Owner WHERE pin='"+o+"'");
			try{
				rs.next();
				rs.getString("pin");
			}catch(Exception e){
				rs=dbc.sendQuery("SELECT pin FROM Owner");
				ArrayList<String>pins=new ArrayList<>();
				while(rs.next()){
					pins.add(rs.getString("pin"));
				}
				Random r=new Random();
				// boolean good=false;
				// int newPin=0;
				// while(!good){
				// 	if(pins.size()>9990)return;
				// 	newPin=r.nextInt(10000);
				// 	for(int i=0;i<pins.size();i++){
				// 		if(newPin==(Integer.valueOf(pins.get(i)))){
				// 			good=true;
				// 			break;
				// 		}
				// 	}
				// }
				dbc.sendQuery("INSERT INTO Customer VALUES("+r.nextInt(1000000000)+",	'Placeholder Name',	'Placeholder Address',	'"+o+"')");
			}
			if(first)dbc.sendQuery("INSERT INTO PrimaryOwner VALUES("+accountID+","+o+")");
			first=false;
			dbc.sendQuery("INSERT INTO Owner VALUES("+accountID+","+o+")");


		}
	}
	public static void delete_closed_accounts_and_customers() throws Exception{
		dbc.sendQuery("DELETE FROM Account WHERE closed=1");
		dbc.sendQuery("DELETE FROM Customer WHERE pin IN (SELECT pin FROM OWNER)");
	}
	public static void delete_transactions() throws Exception{
		dbc.sendQuery("DELETE FROM Transaction");
	}
}