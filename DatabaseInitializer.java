import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DatabaseInitializer{
	private static DBConnection dbc=DBConnection.getInstance();
	public static void addDefaultData() throws Exception{
		try{
			addDefaultCustomers();
			addDefaultAccounts();
			addDefaultOwners();
			addRates();
			addDefaultTransactions();
		}catch(Exception e){
			// ResultSet resultSet = dbc.sendQuery("SHOW ERRORS TRIGGER tid_trigger");
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
			e.printStackTrace();
		}
	}
	public static void addDefaultCustomers() throws Exception{
		dbc.sendQuery("INSERT INTO Customer VALUES(361721022,	'Alfred Hitchcock',	'6667 El Colegio #40',	'1234')");
		dbc.sendQuery("INSERT INTO Customer VALUES(231403227,	'Billy Clinton'	,	'5777 Hollister'		,'1468')");
		dbc.sendQuery("INSERT INTO Customer VALUES(412231856,	'Cindy Laugher'	,	'7000 Hollister'		,'3764')");
		dbc.sendQuery("INSERT INTO Customer VALUES(207843218,	'David Copperfill',	'1357 State St'		,'8582')");
		dbc.sendQuery("INSERT INTO Customer VALUES(122219876,	'Elizabeth Sailor',	'4321 State St'		,'3856')");
		dbc.sendQuery("INSERT INTO Customer VALUES(401605312,	'Fatal Castro'	,	'3756 La Cumbre Plaza',	'8193')");
		dbc.sendQuery("INSERT INTO Customer VALUES(201674933,	'George Brush'	,	'5346 Foothill Av',	'9824')");
		dbc.sendQuery("INSERT INTO Customer VALUES(212431965,	'Hurryson Ford'	,	'678 State St',		'3532')");
		dbc.sendQuery("INSERT INTO Customer VALUES(322175130,	'Ivan Lendme'	,	'1235 Johnson Dr',		'8471')");
		dbc.sendQuery("INSERT INTO Customer VALUES(344151573,	'Joe Pepsi'	,	'3210 State St',		'3692')");
		dbc.sendQuery("INSERT INTO Customer VALUES(209378521,	'Kelvin Costner',		'Santa Cruz #3579',	'4659')");
		dbc.sendQuery("INSERT INTO Customer VALUES(212116070,	'Li Kung'		,	'2 People\'\'s Rd Beijing',	'9173')");
		dbc.sendQuery("INSERT INTO Customer VALUES(188212217,	'Magic Jordon',		'3852 Court Rd'	,	'7351')");
		dbc.sendQuery("INSERT INTO Customer VALUES(203491209,	'Nam-Hoi Chung',		'1997 People\'\'s St HK',	'5340')");
		dbc.sendQuery("INSERT INTO Customer VALUES(210389768,	'Olive Stoner',		'6689 El Colegio #151',	'8452')");
		dbc.sendQuery("INSERT INTO Customer VALUES(400651982,	'Pit Wilson',	'911 State St'	,	'1821')");
	}
	public static void addDefaultAccounts() throws Exception{
		dbc.sendQuery("" + 
		"INSERT INTO Account (aid, type) " + 
		"VALUES (17431, 'Student-Checking')");
		
		dbc.sendQuery("" + 
		"INSERT INTO Account (aid, type)  " + 
		"VALUES (54321, 'Student-Checking')");

		dbc.sendQuery("" + 
		"INSERT INTO Account (aid, type)  " + 
		"VALUES (12121, 'Student-Checking')");

		dbc.sendQuery("" + 
		"INSERT INTO Account (aid, type)  " + 
		"VALUES (41725, 'Interest-Checking')");

		dbc.sendQuery("" + 
		"INSERT INTO Account (aid, type)  " + 
		"VALUES (76543, 'Interest-Checking')");

		dbc.sendQuery("" + 
		"INSERT INTO Account (aid, type)  " + 
		"VALUES (93156, 'Interest-Checking')");

		dbc.sendQuery("" + 
		"INSERT INTO Account (aid, type)  " + 
		"VALUES (43942, 'Savings')");

		dbc.sendQuery("" + 
		"INSERT INTO Account (aid, type)  " + 
		"VALUES (29107, 'Savings')");

		dbc.sendQuery("" + 
		"INSERT INTO Account (aid, type)  " + 
		"VALUES (19023, 'Savings')");

		dbc.sendQuery("" + 
		"INSERT INTO Account (aid, type)  " + 
		"VALUES (32156, 'Savings')");

		dbc.sendQuery("" + 
		"INSERT INTO Account (aid, type)  " + 
		"VALUES (53027, 'Pocket')");
		
		dbc.sendQuery("" + 
		"INSERT INTO PocketAccount (aid, linked_aid, type)  " + 
		"VALUES (53027, 12121, 'Pocket')");
		
		dbc.sendQuery("" + 
		"INSERT INTO Account (aid, type)  " + 
		"VALUES (43947, 'Pocket')");
		
		dbc.sendQuery("" + 
		"INSERT INTO PocketAccount (aid, linked_aid, type)  " + 
		"VALUES (43947, 29107, 'Pocket')");
		
		dbc.sendQuery("" + 
		"INSERT INTO Account (aid, type)  " + 
		"VALUES (60413, 'Pocket')");
		
		dbc.sendQuery("" + 
		"INSERT INTO PocketAccount (aid, linked_aid, type)  " + 
		"VALUES (60413, 43942, 'Pocket')");

		dbc.sendQuery("" + 
		"INSERT INTO Account (aid, type)  " + 
		"VALUES (67521, 'Pocket')");
		
		dbc.sendQuery("" + 
		"INSERT INTO PocketAccount (aid, linked_aid, type)  " + 
		"VALUES (67521, 19023, 'Pocket')");
		
	}
	
	public static void addDefaultOwners() throws Exception{
		HashMap<Integer, String[]> ownerMap = new HashMap<>();
		ownerMap.put(17431, new String[]{"Joe Pepsi", "Cindy Laugher", "Ivan Lendme"});
		ownerMap.put(54321, new String[]{"Hurryson Ford", "Cindy Laugher", "Elizabeth Sailor", "Nam-Hoi Chung"});
		ownerMap.put(12121, new String[]{"David Copperfill"});
		ownerMap.put(41725, new String[]{"George Brush", "Fatal Castro", "Billy Clinton"});
		ownerMap.put(76543, new String[]{"Li Kung", "Magic Jordon"});
		ownerMap.put(93156, new String[]{"Kelvin Costner", "Magic Jordon", "Olive Stoner", "Elizabeth Sailor", "Nam-Hoi Chung"});
		ownerMap.put(43942, new String[]{"Alfred Hitchcock", "Pit Wilson", "Hurryson Ford", "Ivan Lendme"});
		ownerMap.put(29107, new String[]{"Kelvin Costner", "Li Kung", "Olive Stoner"});
		ownerMap.put(19023, new String[]{"Cindy Laugher", "George Brush", "Fatal Castro"});
		ownerMap.put(32156, new String[]{"Magic Jordon", "David Copperfill", "Elizabeth Sailor", "Joe Pepsi", "Nam-Hoi Chung", "Olive Stoner"});
		ownerMap.put(53027, new String[]{"David Copperfill"});
		ownerMap.put(43947, new String[]{"Li Kung", "Olive Stoner"});
		ownerMap.put(60413, new String[]{"Alfred Hitchcock", "Pit Wilson", "Elizabeth Sailor", "Billy Clinton"});
		ownerMap.put(67521, new String[]{"Kelvin Costner", "Fatal Castro", "Hurryson Ford"});
		
		Iterator it = ownerMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry) it.next();
			
			int aid = (Integer) pair.getKey();
			String[] owners = (String[]) pair.getValue();
			
			boolean first = true;
			for(String s : owners){
				ResultSet pinResult = dbc.sendQuery("" + 
													"SELECT C.pin " + 
													"FROM Customer C " + 
													"WHERE C.name = '" + s + "'");
				pinResult.next();
				String pin = pinResult.getString("pin");
				
				System.out.println("Adding owner " + pin + " to account " + aid);
				
				if(first){
					dbc.sendQuery("" + 
					"INSERT INTO PrimaryOwner " + 
					"VALUES (" + aid + ", '" + pin + "')");
					first = false;
				}
				
				dbc.sendQuery("" + 
				"INSERT INTO Owner " + 
				"VALUES (" + aid + ", '" + pin + "')");
			}
			it.remove();
		}
		
	}
	
	public static void addRates() throws Exception{
		dbc.sendQuery("INSERT INTO Rate VALUES ('Interest-Checking', 0.055)");
		dbc.sendQuery("INSERT INTO Rate VALUES ('Student-Checking', 0.0)");
		dbc.sendQuery("INSERT INTO Rate VALUES ('Pocket', 0.0)");
		dbc.sendQuery("INSERT INTO Rate VALUES ('Savings', .075)");
	}
	
	public static void addDefaultTransactions() throws Exception{
		LocalDate epoch = LocalDate.ofEpochDay(0);
		TransactionSender.deposit(TransactionSender.getAccount(17431), 200.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 2)));
		TransactionSender.deposit(TransactionSender.getAccount(54321), 21000.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 3)));
		TransactionSender.deposit(TransactionSender.getAccount(12121), 1200.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 3)));
		TransactionSender.deposit(TransactionSender.getAccount(41725), 15000.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 3)));
		TransactionSender.deposit(TransactionSender.getAccount(93156), 2000000.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 3)));
		TransactionSender.top_up(TransactionSender.getAccount(53027), TransactionSender.getAccount(12121), 50.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 4)));
		TransactionSender.deposit(TransactionSender.getAccount(43942), 1289.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 4)));
		TransactionSender.deposit(TransactionSender.getAccount(29107), 34000.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 4)));
		TransactionSender.deposit(TransactionSender.getAccount(19023), 2300.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 5)));
		TransactionSender.top_up(TransactionSender.getAccount(60413), TransactionSender.getAccount(43942), 20.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 5)));
		TransactionSender.deposit(TransactionSender.getAccount(32156), 1000.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 5)));
		TransactionSender.deposit(TransactionSender.getAccount(76543), 8456.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 5)));
		TransactionSender.top_up(TransactionSender.getAccount(43947), TransactionSender.getAccount(29107), 30.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 5)));
		TransactionSender.top_up(TransactionSender.getAccount(67521), TransactionSender.getAccount(19023), 100.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 6)));
		
		TransactionSender.deposit(TransactionSender.getAccount(17431), 8800, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,2)));
		TransactionSender.withdraw(TransactionSender.getAccount(54321), 3000, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,3)));
		TransactionSender.withdraw(TransactionSender.getAccount(76543), 2000, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,5)));
		TransactionSender.purchase(TransactionSender.getAccount(53027), 5, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,5)));
		TransactionSender.withdraw(TransactionSender.getAccount(93156), 1000000, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,6)));
		TransactionSender.write_check(TransactionSender.getAccount(93156), 950000, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,6)));
		TransactionSender.withdraw(TransactionSender.getAccount(29107), 4000, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,6)));
		TransactionSender.collect(TransactionSender.getAccount(43947), TransactionSender.getAccount(29107),10, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,6)));
		TransactionSender.top_up(TransactionSender.getAccount(43947), TransactionSender.getAccount(29107),30, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,6)));
		TransactionSender.transfer(TransactionSender.getAccount(43942), TransactionSender.getAccount(17431),289, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,7)));
		TransactionSender.withdraw(TransactionSender.getAccount(43942), 289, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,7)));
		TransactionSender.pay_friend(TransactionSender.getAccount(60413),TransactionSender.getAccount(67521), 10, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,8)));
		TransactionSender.deposit(TransactionSender.getAccount(93156), 50000, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,8)));
		TransactionSender.write_check(TransactionSender.getAccount(12121), 200, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,8)));
		TransactionSender.transfer(TransactionSender.getAccount(41725), TransactionSender.getAccount(19023),289, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011,3,8)));

		TransactionSender.wire(TransactionSender.getAccount(41725), TransactionSender.getAccount(32156), 4000.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 9)));
		TransactionSender.pay_friend(TransactionSender.getAccount(53027), TransactionSender.getAccount(60413), 10.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 9)));
		TransactionSender.purchase(TransactionSender.getAccount(60413), 15.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 10)));
		TransactionSender.withdraw(TransactionSender.getAccount(93156), 20000.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 12)));
		TransactionSender.write_check(TransactionSender.getAccount(76543), 456, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 12)));
		TransactionSender.top_up(TransactionSender.getAccount(67521), TransactionSender.getAccount(19023), 50.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 12)));
		TransactionSender.pay_friend(TransactionSender.getAccount(67521), TransactionSender.getAccount(53027), 20.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 14)));
		TransactionSender.collect(TransactionSender.getAccount(43947), TransactionSender.getAccount(29107), 15.0, ChronoUnit.DAYS.between(epoch, LocalDate.of(2011, 3, 14)));

	}
	public static void initializeDatabase() throws Exception{
		//Account, Linked(for pocket account) Primary Owner, Owner, Customer, Rate, Transaction
		// ResultSet rs=dbc.sendQuery("SELECT * FROM ALL_OBJECTS WHERE OBJECT_NAME ='ACCOUNT' ");
		// while(rs.next()){
		// 	System.out.println("hi");
		// 	System.out.println(rs.getString("OBJECT_TYPE"));
		// }

		// ResultSet resultSet = dbc.sendQuery("select * from user_errors where type = 'TRIGGER' and name = 'NEWALERT'");
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
			"balance float DEFAULT 0.0, "+
			"closed int DEFAULT 0 CHECK (closed between 0 and 1),"+
			"PRIMARY KEY (aid))");
		System.out.println("Creating customer");
		dbc.sendQuery("CREATE TABLE Customer("+
			"tid int,"+
			"name varchar(20),"+
			"address varchar(50),"+
			"pin varchar(4),"+
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
			"  INTO   :new.tid"+
			"  FROM   dual;"+
			" END;");
		dbc.sendQuery("ALTER TRIGGER tid_trigger ENABLE");
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
			"type varchar(20),"+
			"closed int DEFAULT 0 CHECK (closed between 0 and 1),"+
			"PRIMARY KEY(aid, linked_aid),"+
			"FOREIGN KEY (linked_aid) REFERENCES Account(aid) ON DELETE CASCADE,"+
			"FOREIGN KEY (aid) REFERENCES Account ON DELETE CASCADE)");
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