import java.util.*;  //for ArrayList

public class SpeakingRequestManager{
   private ResearchDb db;
   
   public SpeakingRequestManager(){
      db = new ResearchDb();
   }
   
   public boolean addRequest(String name, String email, ArrayList<String> recipients, String msg){
      boolean success = true;
      
      //for each author
      
         //start transaction
         
         //get author's faculty id
         
         //create query to insert just name, email, msg, facId fields
         
         //call db setData with query and ArrayList of values
         
         //end transaction
         
      return success;
   }
   
   public boolean addRequest(String name, String email, String phone, ArrayList<String> recipients, String msg){
      boolean success = true;
      
      //for each author
      
         //start transaction
         
         //get author's faculty id
         
         //create query to insert just name, email, msg, facId fields
         
         //call db setData with query and ArrayList of values
         
         //end transaction
         
      return success; 
   }
   
   public ArrayList<SpeakingRequest> getRequestsByFaculty(int facId){
      //call db getData to retrieve speaking requests for the given faculty id
      
      //go through returned data, creating an ArrayList of SpeakingRequests
      
      //CHANGE THIS TO RETURN ARRAYLIST WHEN COMPLETE
      return null;
   }

}