import java.sql.*;
import java.util.*;
public class MonthlyStatementResultSet{
	public ResultSet transactions=null;
	public ArrayList<Double> balances=new ArrayList<>();
	public ArrayList<OwnerTuple> owners=new ArrayList<>();
}