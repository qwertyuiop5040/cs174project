public class TransactionSender{
	private static DBConnection dbc=new DBConnection();
	public static void deposit(Account account, double amount) throws Exception{
		if(!account.closed && account.type != account.POCKET){
			double newBalance = account.balance + amount;
			//maybe make this a function
			dbc.sendQuery("UPDATE Account" + 
				" SET balance=" + newBalance +
				" WHERE aid=" + account.aid);

			account.balance = newBalance;
		}else{
			throw new Exception("Your account is either closed or this is a pocket account.");
		}
	}
	public static void top_up(Account account, Account linkedAccount, double amount) throws Exception{
		if(account.closed || linkedAccount.closed){
			throw new Exception("Your account or linked account is closed");
		}else if(account.type!=account.POCKET || linkedAccount==account.POCKET){
			throw new Exception("You can only topup pocket accounts, this is not one or your linked account is a pocket account");
		}else if(linkedAccount.balance<amount){
			throw new Exception("Your linked account does not have sufficient funds to complete the transaction.");
		}else{
			double newLinkedBalance = linkedAccount.balance - amount;
			dbc.sendQuery("UPDATE Account" + 
				" SET balance=" + newBalance +
				" WHERE aid=" + account.aid);

			double newBalance = account.balance + amount;
			dbc.sendQuery("UPDATE PocketAccount" +
				" SET balance=" + newBalance +
				" WHERE aid=" + account.aid);

			linkedAccount.balance = newLinkedBalance;
			linkedAccount.checkBalance();
			account.balance = newBalance;
		}
	}
	public static void withdraw(){
		
	}
	public static void transfer(){
		
	}
	public static void collect(){
		
	}
	public static void pay_friend(){
		
	}
	public static void wire(){
		
	}
	public static void write_check(){
		
	}
	public static void accrue_interest(){
		
	}

}