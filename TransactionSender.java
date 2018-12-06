import java.sql.*;

public class TransactionSender{
	
	private static DBConnection dbc=DBConnection.getInstance();
	
	public static Account getAccount(int aid) throws Exception{
		ResultSet rs=dbc.sendQuery("SELECT * FROM Account WHERE aid=" + aid);
		rs.next();
//		if(rs.next()){
			return new Account(aid, rs.getString("type"), rs.getDouble("balance"), rs.getBoolean("closed"));
//		}
		/*else{
			rs=dbc.sendQuery("SELECT * FROM PocketAccount WHERE aid=" + aid);
			rs.next();
			return new Account(aid, rs.getString("type"), rs.getDouble("balance"), rs.getBoolean("closed"));
		}*/
	}
	
	public static User getUser(String pin) throws Exception{
		ResultSet rs=dbc.sendQuery("SELECT * FROM Customer WHERE pin=" + pin);
		rs.next();
		return new User(pin, rs.getInt("tid"),rs.getString("name"));
	}
	public static void deposit(Account account, double amount, long date) throws Exception{
		if(!account.closed && !account.type.equals(account.POCKET)){
			double newBalance = account.balance + amount;
			update_balance("Account", account, newBalance, date);
		}else{
			throw new Exception("Your account is either closed or this is a pocket account.");
		}
		
		store_transaction("deposit", account, amount, date);
	}
	
	public static void top_up(Account account, Account linkedAccount, double amount, long date) throws Exception{
		if(account.closed || linkedAccount.closed){
			throw new Exception("Your account or linked account is closed");
		}else if(!account.type.equals(account.POCKET) || linkedAccount.type.equals(account.POCKET)){
			throw new Exception("You can only topup pocket accounts from a linked savings/checking account.");
		}else if(linkedAccount.balance<amount){
			throw new Exception("Your linked account does not have sufficient funds to complete the transaction.");
		}else{
			ResultSet rs = dbc.sendQuery("SELECT P.linked_aid " + 
										 "FROM PocketAccount P " + 
										 "WHERE P.aid = '" + account.aid + "'");
			rs.next();
			int linkedAid = rs.getInt("linked_aid");
			if(linkedAid != linkedAccount.aid){
				throw new Exception("You may only top-up your pocket account from the account that is linked with it");
			}
			
			double newLinkedBalance = linkedAccount.balance - amount;
			update_balance("Account", linkedAccount, newLinkedBalance, date);

			double newBalance = account.balance + amount;
			update_balance("Account", account, newBalance, date);
		}
		
		store_transaction("top_up", account, linkedAccount, amount, date);
	}
	
	public static void withdraw(Account account, double amount, long date) throws Exception{
		if(account.closed || account.type.equals(account.POCKET)){
			throw new Exception("Your account is either closed or this is a pocket account.");
		}else if(account.balance < amount){
			throw new Exception("Your account does not have sufficient funds to complete the transaction.");
		}else {
			double newBalance = account.balance - amount;
			update_balance("Account", account, newBalance, date);
		}
		
		store_transaction("withdraw", account, amount, date);
	}
	
	public static void purchase(Account account, double amount, long date) throws Exception{
		if(account.closed || !account.type.equals(account.POCKET)){
			throw new Exception("Your account is either closed or is not a pocket account.");
		}else if(account.balance < amount){
			throw new Exception("Your account does not have sufficient funds to complete the transaction.");
		}else {
			double newBalance = account.balance - amount;
			update_balance("Account", account, newBalance, date);
		}
		
		store_transaction("purchase", account, amount, date);
	}
	
	public static void transfer(Account sourceAccount, Account destAccount, double amount, long date) throws Exception{
		//Verify that the customer who calls this method is an owner of both sourceAccount and destAccount.
		//^Handle this in the GUI, or wherever transfer(...) is called from
		if(sourceAccount.closed || destAccount.closed){
			throw new Exception("One or both of the accounts are closed");
		}else if(sourceAccount.type.equals(Account.POCKET) || destAccount.type.equals(Account.POCKET)){
			throw new Exception("One or both of the accounts is not a savings or checking account.");
		}else if(amount > 2000){
			throw new Exception("Transfer amounts may not exceed $2000.");
		}else if(sourceAccount.balance < amount){
			throw new Exception("Your source account does not have sufficient funds to complete the transaction.");
		}else {
			double newSourceBalance = sourceAccount.balance - amount;
			update_balance("Account", sourceAccount, newSourceBalance, date);
			
			double newDestBalance = destAccount.balance + amount;
			update_balance("Account", destAccount, newDestBalance, date);
		}
		
		store_transaction("transfer", sourceAccount, destAccount, amount, date);
	}
	
	public static void collect(Account account, Account linkedAccount, double amount, long date) throws Exception{
		if(account.closed || linkedAccount.closed){
			throw new Exception("Your account or linked account is closed");
		}else if(!account.type.equals(account.POCKET) || linkedAccount.type.equals(account.POCKET)){
			throw new Exception("You can only collect from a pocket account to a checking or savings account.");
		}else if(account.balance<amount){
			throw new Exception("Your pocket account does not have sufficient funds to complete the transaction.");
		}else{
			ResultSet rs = dbc.sendQuery("SELECT P.linked_aid " + 
										 "FROM PocketAccount P " + 
										 "WHERE P.aid = '" + account.aid + "'");
			rs.next();
			int linkedAid = rs.getInt("linked_aid");
			if(linkedAid != linkedAccount.aid){
				throw new Exception("You may only top-up your pocket account from the account that is linked with it");
			}
			
			double newBalance = account.balance - amount;
			update_balance("Account", account, newBalance, date);
			
			double newLinkedBalance = linkedAccount.balance + (0.97) * amount;
			update_balance("Account", linkedAccount, newLinkedBalance, date);
		}
		
		store_transaction("collect", account, linkedAccount, amount, date);
	}
	
	public static void pay_friend(Account sourceAccount, Account destAccount, double amount, long date) throws Exception{
		if(sourceAccount.closed || destAccount.closed){
			throw new Exception("One or both of the accounts are closed");
		}else if(!sourceAccount.type.equals(Account.POCKET) || !destAccount.type.equals(Account.POCKET)){
			throw new Exception("One or both of the accounts is not a pocket account.");
		}else if(sourceAccount.balance < amount){
			throw new Exception("Your source account does not have sufficient funds to complete the transaction.");
		}else {
			double newSourceBalance = sourceAccount.balance - amount;
			update_balance("Account", sourceAccount, newSourceBalance, date);
			
			double newDestBalance = destAccount.balance + amount;
			update_balance("Account", destAccount, newDestBalance, date);
		}
		
		store_transaction("pay_friend", sourceAccount, destAccount, amount, date);
	}
	
	public static void wire(Account sourceAccount, Account destAccount, double amount, long date) throws Exception{
		//Verify that the customer who calls this method is an owner of sourceAccount.
		//^Handle this in the GUI, or wherever wire(...) is called from
		if(sourceAccount.closed || destAccount.closed){
			throw new Exception("One or both of the accounts are closed");
		}else if(sourceAccount.type.equals(Account.POCKET) || destAccount.type.equals(Account.POCKET)){
			throw new Exception("One or both of the accounts is not a savings or checking account.");
		}else if(sourceAccount.balance < amount){
			throw new Exception("Your source account does not have sufficient funds to complete the transaction.");
		}else {
			double newSourceBalance = sourceAccount.balance - amount;
			update_balance("Account", sourceAccount, newSourceBalance, date);
			
			double newDestBalance = destAccount.balance + (0.98) * amount;
			update_balance("Account", destAccount, newDestBalance, date);
		}
		
		store_transaction("wire", sourceAccount, destAccount, amount, date);
	}
	
	public static void write_check(Account account, double amount, long date) throws Exception{
		ResultSet maxCheckID = dbc.sendQuery( "SELECT MAX(T.checkID) as checkID " + 
											  "FROM Transaction T " + 
											  "WHERE T.type = 'write_check' AND T.aid1 = " + account.aid );
		
		maxCheckID.next();
		
		int checkID = maxCheckID.getInt("checkID") + 1;
		
		if(account.closed){
			throw new Exception("Your account is closed");
		}else if(!account.type.equals(account.STUDENT_CHECKING) && !account.type.equals(account.INTEREST_CHECKING)){
			throw new Exception("You may only write checks from checking accounts. Your account type is:" + account.type);
		}else if(account.balance < amount){
			throw new Exception("Your account does not have sufficient funds to complete the transaction.");
		}else {
			double newBalance = account.balance - amount;
			update_balance("Account", account, newBalance, date);
		}
		
		store_transaction("write_check", account, null, amount, checkID, date);
	}
	public static double update_balance(String type,double amount, int aid1, int aid){
		switch(type) {
				case "deposit":				return -amount;
				case "top_up": 				return(aid1 == aid)? -amount:amount;
				case "withdraw":			return amount;
				case "purchase":			return amount;
				case "transfer":			return(aid1 == aid)? amount:-amount;
				case "collect":				return (aid1 == aid)? amount:(-0.97) * amount;
				case "pay_friend":			return(aid1 == aid)? amount:-amount;
				case "wire":				return (aid1 == aid)? amount:(-0.98) * amount;
				case "write_check":			return amount;
				case "accrue_interest":		return -amount;
				case "close_account":		return 0;
				default:return amount;
			}
	}
	public static void accrue_interest(Account account, long date) throws Exception{
		
		ResultSet rs = 					  dbc.sendQuery("SELECT * " + 
														"FROM Transaction T " + 
														"WHERE (T.aid1 = " + account.aid + " OR T.aid2 = " + account.aid + ")" + 
														"AND " + date + " - T.daysSince1970 < 30" + " " + 
														"ORDER BY T.daysSince1970 DESC");
		double averageDailyBalance = 0;
		double currentBalance = account.balance;
		
		long earliestDate = date - 30;
		
		while(rs.next()){
			String type = rs.getString("type");
			int aid1 = rs.getInt("aid1");
			int aid2 = rs.getInt("aid2");
			Double amount = rs.getDouble("amount");
			long currentDate = rs.getLong("daysSince1970");
			
			long dateDifference = date - currentDate;
			averageDailyBalance += dateDifference * currentBalance;
			date = currentDate;
			
			// switch(type) {
			// 	case "deposit":				currentBalance -= amount;
			// 								break;
			// 	case "top_up": 				if(aid1 == account.aid) currentBalance -= amount;
			// 								else					currentBalance += amount;
			// 								break;
			// 	case "withdraw":			currentBalance += amount;
			// 								break;
			// 	case "purchase":			currentBalance += amount;
			// 								break;
			// 	case "collect":				if(aid1 == account.aid) currentBalance += amount;
			// 								else					currentBalance -= (0.97) * amount;
			// 								break;
			// 	case "pay_friend":			if(aid1 == account.aid) currentBalance += amount;
			// 								else					currentBalance -= amount;
			// 								break;
			// 	case "wire":				if(aid1 == account.aid) currentBalance += amount;
			// 								else					currentBalance -= (0.98) * amount;
			// 								break;
			// 	case "write_check":			currentBalance += amount;
			// 								break;
			// 	case "accrue_interest":		currentBalance -= amount;
			// }
			currentBalance+=update_balance(type,amount, aid1, account.aid);
		}
		
		averageDailyBalance += (date - earliestDate) * currentBalance;	//for final transaction
		
		averageDailyBalance /= 30;
		
		ResultSet rate = dbc.sendQuery("SELECT R.rate " + 
									   "FROM Rate R " + 
									   "WHERE R.type = " + "'" + account.type + "'");
		rate.next();
		double interestRate = rate.getDouble("rate");
		
		double toBeAdded = (interestRate / 12) * averageDailyBalance;
		double newBalance = account.balance + toBeAdded;
		update_balance("Account", account, newBalance, date);
		
		store_transaction("accrue_interest", account, toBeAdded, date);
	}
	
	private static void update_balance(String table, Account account, double newBalance, long date) throws Exception{
		dbc.sendQuery("UPDATE " + table +  
				" SET balance=" + newBalance +
				" WHERE aid=" + account.aid);
		account.balance = newBalance;
		if(account.balance<=0.01) close_account(table, account, date);
		account.checkBalance();
	}
	
	private static void close_account(String table, Account account, long date) throws Exception{
		dbc.sendQuery("UPDATE " + table +  
					 " SET closed=" + 1 +
					 " WHERE aid=" + account.aid);
					 
		store_transaction("close_account", account, 0, date);
	}
	
	private static void store_transaction(String type, Account account1, Account account2, double amount, int checkID, long date) throws Exception{
		int aid1;
		int aid2;
		
		if(account1 != null) aid1 = account1.aid;
		else				 aid1 = 0;
		
		if(account2 != null) aid2 = account2.aid;
		else				 aid2 = 0;
		
		if(!type.equals("write_check")) checkID = 0;
		
		dbc.sendQuery(  "INSERT INTO Transaction " +
						"VALUES (" + "0, " + "'" + type + "'" + "," + aid1 + "," + aid2 + "," + amount + "," + checkID + "," + date + ")" );
				
	}
	
	private static void store_transaction(String type, Account account1, Account account2, double amount, long date) throws Exception{
		store_transaction(type, account1, account2, amount, 0, date);
	}
	
	private static void store_transaction(String type, Account account1, double amount, long date) throws Exception{
		store_transaction(type, account1, null, amount, 0, date);
	}

}