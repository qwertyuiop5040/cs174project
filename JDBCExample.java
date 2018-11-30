import java.sql.*;

public class JDBCExample {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@cloud-34-133.eci.ucsb.edu:1521:XE";

    //  Database credentials
    static final String USERNAME = "hwang00";
    static final String PASSWORD = "9594458";

    public static void test(){
        DBConnection dbc=new DBConnection();
        try{
            ResultSet rs=dbc.sendQuery("SELECT cid, cname, city, discount FROM cs174.Customers");
                while(rs.next()){
                    //Retrieve by column name
                    String cid  = rs.getString("cid");
                    String cname = rs.getString("cname");
                    String city = rs.getString("city");
                    double discount = rs.getDouble("discount");

                    //Display values
                    System.out.print("cid: " + cid);
                    System.out.print(", cname: " + cname);
                    System.out.print(", city: " + city);
                    System.out.println(", discount: " + discount);
                }
                ResultSet rs2 = dbc.sendQuery("SELECT cname, city, discount FROM cs174.Customers");
                //STEP 5: Extract data from result set
                while(rs2.next()){
                    //Retrieve by column name
                    // String cid  = rs2.getString("cid");
                    String cname = rs2.getString("cname");
                    String city = rs2.getString("city");
                    double discount = rs2.getDouble("discount");

                    //Display values
                    // System.out.print("cid: " + cid);
                    System.out.print(", cname: " + cname);
                    System.out.print(", city: " + city);
                    System.out.println(", discount: " + discount);
                }
            
                rs.close();
                rs2.close();
            }catch(Exception e){
                e.printStackTrace();
                dbc.close();
            }
    }
    public static void test1(){
    	DatabaseInitializer.initializeDatabase();
    	DatabaseInitializer.wipeDatabase();
    }
    public static void main(String[]args){
        // test();
        test1();
    }
    // public static void main(String[] args) {
    //     Connection conn = null;
    //     Statement stmt = null;
    //     try{
    //         //STEP 2: Register JDBC driver
    //         Class.forName(JDBC_DRIVER);

    //         //STEP 3: Open a connection
    //         System.out.println("Connecting to a selected database...");
    //         conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    //         System.out.println("Connected database successfully...");

    //         //STEP 4: Execute a query
    //         System.out.println("Creating statement...");
    //         stmt = conn.createStatement();

    //         String sql = "SELECT cid, cname, city, discount FROM cs174.Customers";
    //         ResultSet rs = stmt.executeQuery(sql);
    //         //STEP 5: Extract data from result set
    //         while(rs.next()){
    //             //Retrieve by column name
    //             String cid  = rs.getString("cid");
    //             String cname = rs.getString("cname");
    //             String city = rs.getString("city");
    //             double discount = rs.getDouble("discount");

    //             //Display values
    //             System.out.print("cid: " + cid);
    //             System.out.print(", cname: " + cname);
    //             System.out.print(", city: " + city);
    //             System.out.println(", discount: " + discount);
    //         }
    //         String sql2 = "SELECT cname, city, discount FROM cs174.Customers";
    //         ResultSet rs2 = stmt.executeQuery(sql2);
    //         //STEP 5: Extract data from result set
    //         while(rs2.next()){
    //             //Retrieve by column name
    //             // String cid  = rs2.getString("cid");
    //             String cname = rs2.getString("cname");
    //             String city = rs2.getString("city");
    //             double discount = rs2.getDouble("discount");

    //             //Display values
    //             // System.out.print("cid: " + cid);
    //             System.out.print(", cname: " + cname);
    //             System.out.print(", city: " + city);
    //             System.out.println(", discount: " + discount);
    //         }
    //         rs.close();
    //     }catch(SQLException se){
    //         //Handle errors for JDBC
    //         se.printStackTrace();
    //     }catch(Exception e){
    //         //Handle errors for Class.forName
    //         e.printStackTrace();
    //     }finally{
    //         //finally block used to close resources
    //         try{
    //             if(stmt!=null)
    //                 conn.close();
    //         }catch(SQLException se){
    //         }// do nothing
    //         try{
    //             if(conn!=null)
    //                 conn.close();
    //         }catch(SQLException se){
    //             se.printStackTrace();
    //         }//end finally try
    //     }//end try
    //     System.out.println("Goodbye!");
    // }//end main
}//end JDBCExample