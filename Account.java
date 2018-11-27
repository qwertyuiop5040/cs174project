public class Account{
	public static final String CHECKING="Checking";
	public static final String SAVINGS="Savings";
	public static final String POCKET="Pocket";

	public String type;
	public String customerPIN;
	public int aid;
	public double balance;
	public boolean closed;
	public void checkBalance(){
		if(balance <= 0.01)
			closed = true;
	}
}