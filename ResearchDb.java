import java.sql.*;
import java.util.*;  //for Date and ArrayList

/**
 * Provides methods to open and close a connection to and access and alter the data 
 * within the facresearchdb database.
 * @author Team 17: Fareed Abolhassani, Abdulaziz Alshkrah, Craig Price, Cynthia Zachar
 */
public class ResearchDb{
   private String uri = "jdbc:mysql://localhost/facresearchdb?autoReconnect=true&useSSL=false";
   private String driver = "com.mysql.jdbc.Driver";
   private String user = "root";
   private String password = "student";
   private Connection conn = null;
   private static final ResearchDb INSTANCE = new ResearchDb();
   
   /**
    * Following the Singleton pattern, returns an existing instant of
    * this class
    * @return a ResearchDb instance
    */
   public static ResearchDb getInstance(){
      return INSTANCE;
   }
   
   /**
    * Attempts to connect to MySQL database
    */
   public void connect() throws DLException{
      if(conn == null){
         try{
            Class.forName(driver);
            conn = DriverManager.getConnection(uri, user, password);
         }
         catch(ClassNotFoundException cnfe){
            throw new DLException(cnfe, getCurTime(), "ResearchDb:connect",
                                    "MySQL driver not found.");
         }
         catch(SQLException sqle){
            throw new DLException(sqle, getCurTime(), "ResearchDb:connect",
                                    "User: " + user);
         }
         catch(Exception e){
            throw new DLException(e, getCurTime(), "ResearchDb:connect");
         }
      }
   }  //end connect
   
   /**
    * Attempts to close MySQL connection
    * @return true if connection closure is successful; false otherwise
    */
   public void close() throws DLException{
      try{
         if(conn != null && conn.getAutoCommit()){
            conn.close();
            conn = null;
         }
      }
      catch(SQLException sqle){
         throw new DLException(sqle, getCurTime(), "ResearchDb:close");
      }
      catch(Exception e){
         throw new DLException(e, getCurTime(), "ResearchDb:close");
      }
   }
   
   /**
    * Retrieves data from the database with a prepared statement, including column names
    * @param prepStr          the SQL query (using placeholders) to be prepared
    * @param values           an ArrayList of values to bind to the prepared statement
    * @param includeColNames  indicates whether to include column names with returned data          
    * @return the requested data in the form of a 2D ArrayList
    */
   public ArrayList<ArrayList<String>> getData(String prepStr, ArrayList<String> values, boolean includeColNames) throws DLException{
      connect();
      ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
      ArrayList<String> row = new ArrayList<String>();
      
      //prepare statement
      PreparedStatement stmt = prepare(prepStr, values);
      
      //execute & get data
      try{
         ResultSet result = stmt.executeQuery();
         ResultSetMetaData rsmd = result.getMetaData();
         int numCols = rsmd.getColumnCount();
      
         //add column names to 2D ArrayList, if desired
         if(includeColNames){
            for(int i = 1; i <= numCols; i++){
               row.add(rsmd.getColumnName(i));
            }
            data.add(row);
            row = new ArrayList<String>();
         }
         
         //iterate through result, adding rows to the 2D ArrayList
         while(result.next()){
            //iterate through columns, adding field values to row ArrayList  
            for(int i = 1; i <= numCols; i++){   
               row.add(result.getString(i));
            }
            data.add(row);
            row = new ArrayList<String>();
         }
      }
      catch(SQLException sqle){
         throw new DLException(sqle, getCurTime(), "ResearchDb:getData(String,ArrayList)", 
                                 "Prepared string: " + prepStr, "Values: " + values);
      }
      catch(Exception e){
         throw new DLException(e, getCurTime(), "ResearchDb:getData(String,ArrayList)",
                                 "Prepared string: " + prepStr, "Values: " + values);
      }
      finally{
         close();
      }
      return data;
   }  //end getData
   
   /**
    * Updates the database using a prepared statement
    * @param prepStr  the SQL (using placeholders) to be prepared
    * @param values   an ArrayList of values to bind to the prepared statement  
    * @return true if update was successful; false otherwise
    */
   public boolean setData(String sqlStr, ArrayList<String> values) throws DLException{
      connect();
      int rowCount = 0;
      PreparedStatement stmt = prepare(sqlStr, values);
      try{
         rowCount = stmt.executeUpdate();
      }
      catch(SQLException sqle){
         throw new DLException(sqle, getCurTime(), "ResearchDb:setData)", 
                                 "Prepared string: " + sqlStr, "Values: " + values);
      }
      catch(Exception e){
         throw new DLException(e, getCurTime(), "ResearchDb:setData",
                                 "Prepared string: " + sqlStr, "Values: " + values);
      }
      finally{
         close();
      }
      return rowCount > 0;
   }
   
   /**
    * Inserts data into the database using a prepared statement
    * @param prepStr  the SQL (using placeholders) to be prepared
    * @param values   an ArrayList of values to bind to the prepared statement
    * @return the generated id of the last inserted record
    */
   public int insertData(String sqlStr, ArrayList<String> values) throws DLException{
      connect();
      int lastId = -1;
      PreparedStatement stmt = prepare(sqlStr, values);
      try{
         stmt.executeUpdate();
         ResultSet result = stmt.getGeneratedKeys();
         if(result.next()){
            lastId = result.getInt(1);
         }
      }
      catch(SQLException sqle){
         throw new DLException(sqle, getCurTime(), "ResearchDb:insertData)", 
                                 "Prepared string: " + sqlStr, "Values: " + values);
      }
      catch(Exception e){
         throw new DLException(e, getCurTime(), "ResearchDb:insertData",
                                 "Prepared string: " + sqlStr, "Values: " + values);
      }
      finally{
         close();
      }
      return lastId;
   }
   
   /**
    * Creates a prepared statement for execution
    * @param prepStr    the SQL to prepare
    * @param values     an ArrayList of values to bind to the prepared statement
    * @return the resulting PreparedStatement
    */
   public PreparedStatement prepare(String prepStr, ArrayList<String> values) throws DLException{
      PreparedStatement stmt = null;
      
      try{
         //prepare statement
         stmt = conn.prepareStatement(prepStr, Statement.RETURN_GENERATED_KEYS);
         
         //bind values
         int numVals = values.size();
         for(int i = 0; i < numVals; i++){
            stmt.setString(i + 1, values.get(i));  //note that param indexing starts at 1
         }
      }
      catch(SQLException sqle){
         throw new DLException(sqle, getCurTime(), "ResearchDb:prepare", 
                                 "Prepared string: " + prepStr, "Values: " + values);
      }
      catch(Exception e){
         throw new DLException(e, getCurTime(), "ResearchDb:prepare", 
                                 "Prepared string: " + prepStr, "Values: " + values);
      }
      return stmt;
   }  //end prepare
   
   /**
    * Begins a transaction
    */
   public void startTrans() throws DLException{
      try{
         if(conn != null){
            conn.setAutoCommit(false);
         }
      }
      catch(SQLException sqle){
         throw new DLException(sqle, getCurTime(), "ResearchDb:startTrans");
      }
   }
  
   /**
    * Ends a transaction
    */
   public void endTrans() throws DLException{
      try{
         if(conn != null){
            conn.setAutoCommit(true);
         }
      }
      catch(SQLException sqle){
         throw new DLException(sqle, getCurTime(), "ResearchDb:endTrans");
      }
   }
   
   /**
    * Returns the database to the state it was in before transaction statements were attempted
    */
   public void rollbackTrans() throws DLException{
      try{
         if(conn != null){
            conn.rollback();
         }
      }
      catch(SQLException sqle){
         throw new DLException(sqle, getCurTime(), "ResearchDb:rollbackTrans");
      }
   }
   
   /**
    * Commits changes made at the end of a transaction
    */
   public void commitTrans() throws DLException{
      try{
         if(conn != null){
            conn.commit();
         }
      }
      catch(SQLException sqle){
         throw new DLException(sqle, getCurTime(), "ResearchDb:commitTrans");
      }
   }
   
   /**
    * Gets the current time for the purpose of logging the time of any Exceptions that occur.
    * @return the current time
    */
   private String getCurTime(){
      return (new java.util.Date()).toString();
   }
   
}  //end ResearachDb class