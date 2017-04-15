import java.io.*; //for writing to log

/**
 * Data Layer exception class that defines a custom Exception that maintains a log
 * of all Exceptions that arise while attempting to access a MySQL Database.
 */
public class DLException extends Exception{
   private final String LOG_FILENAME = "log.txt";
   private Exception e;
   private String timestamp;
   private String location;
   private String[] details;
   
   /**
    * Simple constructor
    * @param _e   the Exception that occured
    */
   public DLException(Exception _e){
      e = _e;
      log();
   }
   
   /**
    * Constructor with timestamp and additional details
    * @param _e         the Exception that occured
    * @param time       the time that the Exception occured
    * @param _location  description of the location in the code that an Exception occured
    * @param _details   a variable number of String parameters that give additional details about the problem that occurred
    */
   public DLException(Exception _e, String time, String _location, String... _details){
      e = _e;
      timestamp = time;
      location = _location;
      details = _details;
      log();
   }
   
   /**
    * Writes information about DLExceptions to a log file
    */
   private void log(){
      try{
         PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILENAME, true));
         
         //log timestamp, error location, and Exception message
         pw.println(timestamp + "\t" + location + System.getProperty("line.separator") + e.getMessage());

         //log additional error details
         for(String detail:details){
            pw.println(detail);
         }
         
         //separate records with a new line
         pw.println();
         
         pw.close();
      }
      catch(IOException ioe){
         //failed to write to log...
      }
   }  //end log method
   
}  //end DLException class