import java.util.*;

/**
 * 
 */
public class PublicationManagerTester{

   public static void main(String[] args){
      new PublicationManagerTester();
   }
   
   public PublicationManagerTester(){
      PublicationManager manager = new PublicationManager();
      displayPublications(manager.fetchPublicationsByFaculty("Steve Zilora"));
      displayPublications(manager.fetchPublicationsByKeyword("PERFORMANCE"));
   }
   
   private void displayPublications(ArrayList<Publication> papers){
      for(Publication paper : papers){
         System.out.println(paper);
      }
   }

}