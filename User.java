public class User{
	//store pin
	public User(){

	}
	public User(String pin, int accountID, String name){
		this(pin, accountID, name, false, null);
	}
	public User(String pin, int accountID, String name, boolean isLoggedIn, Account [] accounts){
		this.pin=pin;
		this.accountID=accountID;
		this.name=name;
		this.isLoggedIn=isLoggedIn;
		if(accounts!=null)
			this.accounts=accounts;
		else
			this.accounts=new Account[100];
	}
	public String name;
	public String pin;
	public boolean isLoggedIn;
	public int accountID;
	public Account[] accounts;
}