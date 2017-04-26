import java.util.*;

/**
 * Class to test methods of the PublicationManager class
 */
public class PublicationManagerTester{

   public static void main(String[] args){
      new PublicationManagerTester();
   }
   
   public PublicationManagerTester(){
      PublicationManager manager = new PublicationManager();
      
      System.out.println("-------Testing fetch by faculty---------");
      displayPublications(manager.fetchPublicationsByFaculty("Steve Zilora"));
      
      System.out.println("-------Testing fetch by keyword---------");
      displayPublications(manager.fetchPublicationsByKeyword("PERFORMANCE"));
      
      System.out.println("-------Testing add publication---------");
      Publication paper = new Publication("A title!","An abstract","A citation",getParamArrayList("Basket Weaving","Nonsense"),getParamArrayList("Steve Zilora","Dan Bogaard"));
      printOperationStatus(manager.addPublication(paper));
      
      System.out.println("-------Testing update publication---------");
      paper.setAbstract("A revised abstract");
      paper.setAuthors(getParamArrayList("Dan Bogaard"));
      paper.setKeywords(getParamArrayList("Nonsense","The Meaning of Life"));
      printOperationStatus(manager.updatePublication(paper));
      
      System.out.println("-------Testing remove publication---------");
      printOperationStatus(manager.removePublication(20));
      
   }
   
   private void displayPublications(ArrayList<Publication> papers){
      for(Publication paper : papers){
         System.out.println(paper);
      }
   }
   
   /**
    * Fills an ArrayList with a series of Strings
    * @return  an ArrayList containing the given Strings
    */
   private ArrayList<String> getParamArrayList(String... params){
      ArrayList<String> paramList = new ArrayList<String>();
      for(String param : params){
         paramList.add(param);
      }
      return paramList;
   }
   
   private void printOperationStatus(boolean status){
      if(status){
         System.out.println("Operation successful -- check db to make sure everything is correct\n");
      }
      else{
         System.out.println("Operation unsuccessful -- check db to make sure nothing was changed\nMake sure parameters for methods make sense\n");
      }
   }

}