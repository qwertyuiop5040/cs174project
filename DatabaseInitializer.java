import java.sql.*;

public class DatabaseInitializer{
	private static DBConnection dbc=DBConnection.getInstance();
	public static void initializeDatabase(){
		//Account, Linked(for pocket account) Primary Owner, Owner, Customer, Rate, Transaction
		dbc.sendQuery("CREATE TABLE Account("+
			"aid int," +
			"type varchar(20),"+
			"balance float, "+
			"closed int CHECK (closed between 0 and 1),"+
			"PRIMARY KEY (aid))");
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
		dbc.sendQuery("CREATE SEQUENCE tid_inc START WITH 1;");
		dbc.sendQuery("CREATE OR REPLACE TRIGGER tid_trigger "+
			"BEFORE INSERT ON Transaction "+
			"FOR EACH ROW"+
			"BEGIN"+
			"  SELECT inc.NEXTVAL"+
			"  INTO   :new.id"+
			"  FROM   dual;"+
			"END;"+
		"/");
		dbc.sendQuery("CREATE TABLE Owner("+
			"aid int,"+
			"pin varchar(4),"+
			"PRIMARY KEY (aid, pin),"+
			"FOREIGN KEY (aid) REFERENCES Account,"+
			"FOREIGN KEY (pin) REFERENCES Customer)");
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
	public static void wipeDatabase(){
		String[]tableNames={"Account", "Customer","Rate","Transaction",
		"Owner","PrimaryOwner","PocketAccount"};
		for(String name:tableNames){
			dbc.sendQuery("DROP TABLE " + name + "PURGE");
		}
		dbc.sendQuery("DROP SEQUENCE tid_inc");
		dbc.sendQuery("DROP TRIGGER tid_trigger");
	}
}