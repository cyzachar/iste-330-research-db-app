import java.awt.event.*;
import java.util.*;

public class LoginHandler implements ActionListener
{   
   public void actionPerformed(ActionEvent ae)
   {
      FacultyManager manager = new FacultyManager();
      Faculty fac = new Faculty();
     
      fac = manager.checkLogin(fac.getPassword(), fac.getEmail());
      if(fac == null)
      {
         System.out.println("login failed");
      }
      else
      {
         System.out.println("Login successful:\n" + fac);
      }
      
      fac = new Faculty( fac.getId() ,fac.getFirstName(), fac.getLastName(), fac.getPassword(), fac.getEmail() );
      displayPublications(manager.getPublications(fac)); 
      
      System.out.println("authors valid?: " + manager.validateAuthors(getParamArrayList(fac.getFirstName(),fac.getLastName())));
   }
   
   private void displayPublications(ArrayList<Publication> papers)
   {
      for(Publication paper : papers)
      {
         System.out.println(paper);
      }
   }
   
   private ArrayList<String> getParamArrayList(String... params)
   {
      ArrayList<String> paramList = new ArrayList<String>();
      for(String param : params)
      {
         paramList.add(param);
      }
      return paramList;
   }

}