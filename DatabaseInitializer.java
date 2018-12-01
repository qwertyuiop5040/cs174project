import java.sql.*;

public class DatabaseInitializer{
	private static DBConnection dbc=DBConnection.getInstance();
	public static void initializeDatabase() throws Exception{
		//Account, Linked(for pocket account) Primary Owner, Owner, Customer, Rate, Transaction
		// ResultSet rs=dbc.sendQuery("SELECT * FROM ALL_OBJECTS WHERE OBJECT_NAME ='ACCOUNT' ");
		// while(rs.next()){
		// 	System.out.println("hi");
		// 	System.out.println(rs.getString("OBJECT_TYPE"));
		// }
		
		// ResultSet resultSet = dbc.sendQuery("SELECT * FROM USER_OBJECTS");
		// ResultSetMetaData rsmd = resultSet.getMetaData();
		// int columnsNumber = rsmd.getColumnCount();
		// while (resultSet.next()) {
		//     for (int i = 1; i <= columnsNumber; i++) {
		//         if (i > 1) System.out.print(",  ");
		//         String columnValue = resultSet.getString(i);
		//         System.out.print(columnValue + " " + rsmd.getColumnName(i));
		//     }
		//     System.out.println("");
		// }
		System.out.println("Creating account");
		dbc.sendQuery("CREATE TABLE Account("+
			"aid int," +
			"type varchar(20),"+
			"balance1 float, "+
			"closed1 int CHECK (closed1 between 0 and 1),"+
			"PRIMARY KEY (aid))");
		System.out.println("Creating customer");
		dbc.sendQuery("CREATE TABLE Customer("+
			"tid int,"+
			"pin varchar(4),"+
			"name varchar(20),"+
			"address varchar(50),"+
			"PRIMARY KEY (pin))");
		dbc.sendQuery("CREATE TABLE Rate("+
			"type varchar(20),"+
			"rate float,"+
			"PRIMARY KEY (type))");
		dbc.sendQuery("CREATE TABLE Transaction("+
			"tid int,"+
			"type varchar(20),"+
			"aid1 int,"+
			"aid2 int,"+
			"amount float,"+
			"daysSince1970 int,"+
			"checkID int,"+
			"PRIMARY KEY (tid))");
		dbc.sendQuery("CREATE SEQUENCE tid_inc START WITH 1");
		System.out.println("Creating trigger");
		dbc.sendQuery("CREATE OR REPLACE TRIGGER tid_trigger "+
			" BEFORE INSERT ON Transaction "+
			" FOR EACH ROW"+
			" BEGIN"+
			"  SELECT tid_inc.NEXTVAL"+
			"  INTO   :new.id"+
			"  FROM   dual"+
			" END/");
		dbc.sendQuery("CREATE TABLE Owner("+
			"aid int,"+
			"pin varchar(4),"+
			"PRIMARY KEY (aid, pin),"+
			"FOREIGN KEY (aid) REFERENCES Account,"+
			"FOREIGN KEY (pin) REFERENCES Customer)");
		System.out.println("Creating PrimaryOwner");
		dbc.sendQuery("CREATE TABLE PrimaryOwner("+
			"aid int,"+
			"pin varchar(4),"+
			"PRIMARY KEY (aid),"+
			"FOREIGN KEY (aid) REFERENCES Account,"+
			"FOREIGN KEY (pin) REFERENCES Customer)");
		dbc.sendQuery("CREATE TABLE PocketAccount("+
			"aid int,"+
			"linked_aid int,"+
			"closed int CHECK (closed between 0 and 1),"+
			"type varchar(20),"+
			"balance float,"+
			"PRIMARY KEY(aid, linked_aid),"+
			"FOREIGN KEY (linked_aid) REFERENCES account(aid) ON DELETE CASCADE)");
	}
	public static void wipeDatabase() throws Exception{
		try{
			dbc.sendQuery("DROP TRIGGER tid_trigger");
		}catch(Exception e){
			e.printStackTrace();
			//does nothign
		}
		try{
			dbc.sendQuery("DROP SEQUENCE tid_inc");	
		}catch(Exception e){
			//does nothign
			e.printStackTrace();
		}
		String[]tableNames={"ACCOUNT", "CUSTOMER","RATE","Transaction",
		"Owner","PrimaryOwner","PocketAccount"};
		for(String name:tableNames){
			try{
				String query = "DROP TABLE " + name + " PURGE";
				dbc.sendQuery(query);
			}catch(Exception e){
				System.out.println(name);
				// e.printStackTrace();
				//does nothign
			}
		}
		
		// dbc.sendQuery("DROP DATABASE INCLUDING BACKUPS;");
	}
}