public class Account{
	public static final String STUDENT_CHECKING="Student-Checking";
	public static final String INTEREST_CHECKING="Interest-Checking";
	public static final String SAVINGS="Savings";
	public static final String POCKET="Pocket";
	public Account(int aid,String type, double balance, boolean closed){
		this.aid=aid;
		this.type=type;
		this.balance=balance;
		this.closed=closed;
	}
	public String type;
	public int aid;
	public double balance;
	public boolean closed;
	public void checkBalance(){
		if(balance <= 0.01)
			closed = true;
	}
}