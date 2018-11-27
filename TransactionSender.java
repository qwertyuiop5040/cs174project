public class TransactionSender{
	private static DBConnection dbc=new DBConnection();
	public static void deposit(Account account, double amount) throws Exception{
		if(!account.closed && account.type != account.POCKET){
			double newBalance = account.balance + amount;
			//maybe make this a function
			update_balance("Account", account, newBalance);
			
		}else{
			throw new Exception("Your account is either closed or this is a pocket account.");
		}
	}
	public static void top_up(Account account, Account linkedAccount, double amount) throws Exception{
		if(account.closed || linkedAccount.closed){
			throw new Exception("Your account or linked account is closed");
		}else if(account.type!=account.POCKET || linkedAccount.type==account.POCKET){
			throw new Exception("You can only topup pocket accounts, this is not one or your linked account is a pocket account");
		}else if(linkedAccount.balance<amount){
			throw new Exception("Your linked account does not have sufficient funds to complete the transaction.");
		}else{
			double newLinkedBalance = linkedAccount.balance - amount;
			update_balance("Account", linkedAccount, newLinkedBalance);

			double newBalance = account.balance + amount;
			update_balance("PocketAccount", account, newBalance);
		}
	}
	public static void withdraw(Account account, double amount) throws Exception{
		if(account.closed || account.type == account.POCKET){
			throw new Exception("Your account is either closed or this is a pocket account.");
		}else if(account.balance < amount){
			throw new Exception("Your account does not have sufficient funds to complete the transaction.");
		}else {
			double newBalance = account.balance - amount;
			update_balance("Account", account, newBalance);
		}
	}
	public static void purchase(Account account, double amount) throws Exception{
		if(account.closed || account.type != account.POCKET){
			throw new Exception("Your account is either closed or is not a pocket account.");
		}else if(account.balance < amount){
			throw new Exception("Your account does not have sufficient funds to complete the transaction.");
		}else {
			double newBalance = account.balance - amount;
			update_balance("PocketAccount", account, newBalance);
		}
	}
	public static void transfer(Account sourceAccount, Account destAccount, double amount) throws Exception{
		//TODO: Verify that sourceAccount and destAccount have at least one owner in common.
		//or maybe this is handled by GUI/Interface
		if(sourceAccount.closed || destAccount.closed){
			throw new Exception("One or both of the accounts are closed");
		}else if(sourceAccount.type == Account.POCKET || destAccount.type == Account.POCKET){
			throw new Exception("One or both of the accounts is not a savings or checking account.");
		}else if(amount > 2000){
			throw new Exception("Transfer amounts may not exceed $2000.");
		}else if(sourceAccount.balance < amount){
			throw new Exception("Your source account does not have sufficient funds to complete the transaction.");
		}else {
			double newSourceBalance = sourceAccount.balance - amount;
			update_balance("Account", sourceAccount, newSourceBalance);
			
			double newDestBalance = destAccount.balance + amount;
			update_balance("Account", destAccount, newDestBalance);
		}
	}
	public static void collect(Account account, Account linkedAccount, double amount) throws Exception{
		if(account.closed || linkedAccount.closed){
			throw new Exception("Your account or linked account is closed");
		}else if(account.type!=account.POCKET || linkedAccount.type==account.POCKET){
			throw new Exception("You can only collect from a pocket account to a checking or savings account.");
		}else if(account.balance<amount){
			throw new Exception("Your pocket account does not have sufficient funds to complete the transaction.");
		}else{
			double newBalance = account.balance - amount;
			update_balance("PocketAccount", account, newBalance);
			
			double newLinkedBalance = linkedAccount.balance + (0.97) * amount;
			update_balance("Account", linkedAccount, newLinkedBalance);
		}
	}
	public static void pay_friend(Account sourceAccount, Account destAccount, double amount) throws Exception{
		if(sourceAccount.closed || destAccount.closed){
			throw new Exception("One or both of the accounts are closed");
		}else if(sourceAccount.type != Account.POCKET || destAccount.type != Account.POCKET){
			throw new Exception("One or both of the accounts is not a pocket account.");
		}else if(sourceAccount.customerPIN == destAccount.customerPIN){
			throw new Exception("You can not pay-friend yourself.");
		}else if(sourceAccount.balance < amount){
			throw new Exception("Your source account does not have sufficient funds to complete the transaction.");
		}else {
			double newSourceBalance = sourceAccount.balance - amount;
			update_balance("PocketAccount", sourceAccount, newSourceBalance);
			
			double newDestBalance = destAccount.balance + amount;
			update_balance("PocketAccount", destAccount, newDestBalance);
		}
	}
	public static void wire(Account sourceAccount, Account destAccount, double amount) throws Exception{
		if(sourceAccount.closed || destAccount.closed){
			throw new Exception("One or both of the accounts are closed");
		}else if(sourceAccount.type == Account.POCKET || destAccount.type == Account.POCKET){
			throw new Exception("One or both of the accounts is not a savings or checking account.");
		}else if(sourceAccount.balance < amount){
			throw new Exception("Your source account does not have sufficient funds to complete the transaction.");
		}else {
			double newSourceBalance = sourceAccount.balance - amount;
			update_balance("Account", sourceAccount, newSourceBalance);
			
			double newDestBalance = destAccount.balance + (0.98) * amount;
			update_balance("Account", destAccount, newDestBalance);
		}
	}
	public static void write_check(Account account, double amount) throws Exception{
		//"Associated with a check transaction is a check number"
		//store in DB? or where?
		if(account.closed){
			throw new Exception("Your account is closed");
		}else if(account.type != account.CHECKING){
			throw new Exception("You may only write checks from checking accounts.");
		}else if(account.balance < amount){
			throw new Exception("Your account does not have sufficient funds to complete the transaction.");
		}else {
			double newBalance = account.balance - amount;
			update_balance("Account", account, newBalance);
		}
	}
	public static void accrue_interest() throws Exception{
		//TODO
	}
	
	private static void update_balance(String table, Account account, double newBalance) throws Exception{
		dbc.sendQuery("UPDATE " + table +  
				" SET balance=" + newBalance +
				" WHERE aid=" + account.aid);
		account.balance = newBalance;
		account.checkBalance();
	}

}