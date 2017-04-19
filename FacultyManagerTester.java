import java.util.*;

public class FacultyManagerTester{

   public static void main(String[] args){
      new FacultyManagerTester();
   }
   
   public FacultyManagerTester(){
      FacultyManager manager = new FacultyManager();
      
      //testing login
      Faculty fac = manager.checkLogin("53","sjz@it.rit.edu");
      if(fac == null){
         System.out.println("login failed");
      }
      else{
         System.out.println("Login successful:\n" + fac);
      }
      
      //testing getPublications
      fac = new Faculty(0,"Steve","Zilora","","");
      displayPublications(manager.getPublications(fac));
      
      //System.out.println("authors valid?: " + manager.validateAuthors(getParamArrayList("Zilora","Dan Bogaard")));
      
   }
   
   private void displayPublications(ArrayList<Publication> papers){
      for(Publication paper : papers){
         System.out.println(paper);
      }
   }
   
   private ArrayList<String> getParamArrayList(String... params){
      ArrayList<String> paramList = new ArrayList<String>();
      for(String param : params){
         paramList.add(param);
      }
      return paramList;
   }

}