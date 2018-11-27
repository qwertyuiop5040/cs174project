import java.sql.*;

public class DBConnection {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@cloud-34-133.eci.ucsb.edu:1521:XE";

    //  Database credentials
    static final String USERNAME = "hwang00";
    static final String PASSWORD = "9594458";

	Connection conn = null;
    Statement stmt = null;


	public DBConnection(){
        initialize();
    }
	public void initialize(){
        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            stmt = conn.createStatement();
            System.out.println("Connected database successfully...");
			
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
			close();
        }
	}
	
	public ResultSet sendQuery(String query) throws Exception{
        return stmt.executeQuery(query);
	}
	
	public void close(){
        System.out.println("Closing Database...");
		try{
            conn.close();
            System.out.println("Closed Database Successfully");
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
	}
	
}//end JDBCExample