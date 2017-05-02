import java.util.ArrayList;

/**
 * Provides methods to verify login credentials, retrieve faculty publications,
 * and validate user-entered data before making changes to the database (edit, add, remove)
 * @author Team 17: Fareed Abolhassani, Abdulaziz Alshkrah, Craig Price, Cynthia Zachar
 */
public class FacultyManager {
	private ResearchDb db;
   private PublicationManager pManager;

   /**
    * Initializes the database and publication manager
    */
	public FacultyManager() {
		db = ResearchDb.getInstance();
      pManager = new PublicationManager();
	}
   
   /**
    * Checks that a email-password combination is valid
    * @param password   the user-entered password
    * @param email      the user-entered email
    * @return a Faculty object if login is successful or null if unsuccessful
    */
	public Faculty checkLogin(String password, String email) {

		String query = "select * from faculty where password = ? and email = ?";

		try {
			Faculty faculty = new Faculty();
			ArrayList<ArrayList<String>> result = db.getData(query, getParamArrayList(password, email), false);

			if (result.size() != 0) {
            ArrayList<String> row = result.get(0);
				faculty.setId(Integer.parseInt(row.get(0)));
				faculty.setFirstName(row.get(1));
				faculty.setLastName(row.get(2));
				faculty.setPassword(row.get(3));
				faculty.setEmail(row.get(4));
				return faculty;
			} else
				return null;
		} catch (DLException ex) {
			return null;
		}
	}
   
   /**
    * Retrieves the publications that a particular faculty member contributed to
    * @param faculty    the faculty member to get publications for
    * @return a list of publications written by the faculty member
    */
	public ArrayList<Publication> getPublications(Faculty faculty){
      return pManager.fetchPublicationsByFaculty(faculty.getFirstName() + " " + faculty.getLastName());
	}
   
   /**
    * Validates publication info before adding the publication to the database
    * @param title      the title of the new publication
    * @param _abstract  the abstract of the new publication
    * @param citation   the citation of the new publication
    * @param keywords   the keywords of the new publication
    * @param authors    the authors of the new publication
    * @return a String describing the status of the operation
    */
   public String addPublication(String title, String _abstract, String citation, ArrayList<String> keywords, ArrayList<String> authors){
      if(validateAuthors(authors)){
         if(pManager.addPublication(new Publication(title, _abstract, citation, keywords, authors))){
            return "Publication was successfully added!";
         }
         else{
            return "Error: Addition of this publication failed.";
         }
      }
      else{
         return "Error: At least one listed author does not exist in the database.";
      }
   }
   
   /**
    * Validates publication info before updating the publication in the database
    * @param id         the new id of the publication
    * @param title      the new title of the publication
    * @param _abstract  the new abstract of the publication
    * @param citation   the new citation of the publication
    * @param keywords   the new keywords of the publication
    * @param authors    the new authors of the publication
    * @return a String describing the status of the operation
    */
   public String editPublication(int id, String title, String _abstract, String citation, ArrayList<String> keywords, ArrayList<String> authors){
      if(validateAuthors(authors)){
         if(pManager.updatePublication(new Publication(id, title, _abstract, citation, keywords, authors))){
            return "Publication was successfully updated!";
         }
         else{
            return "Error: Update operation failed";
         }
      }
      else{
         return "Error: At least one listed author does not exist in the database. Be sure to provide both first and last name for all authors.";
      }
   }
   
   /**
    * Removes a publication from the database
    * @param id   the id of the publication to remove
    * @return true if the deletion was successful; false otherwise
    */
   public boolean deletePublication(int id){
      return pManager.removePublication(id);
   }
   
   /**
    * Checks that authors exist in the faculty database
    * @param authors    the author names to look for
    * @return true if the names were found; false otherwise
    */
   public boolean validateAuthors(ArrayList<String> authors){
      boolean isValid = true;
      String query = "SELECT id FROM faculty WHERE CONCAT(fName,' ',lName) = ?;";
      
      try{
         db.startTrans();
         
         //check that each author is in the database
         for(String author : authors){
            ArrayList<ArrayList<String>> result = db.getData(query, getParamArrayList(author), false);
            if(result.size() < 1){
               isValid = false;
            }
         }
      }
      catch(DLException dle){
         isValid = false;
      }
      
      try{
         //end transaction and close connection
         db.endTrans();
         db.close();
      }
      catch(DLException dle){/*probably shouldn't return null if ending transaction/close fails*/}
      
      return isValid;
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
   
}  //end FacultyManager class