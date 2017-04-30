import java.util.*;  //for ArrayList

/**
 * Provides methods to fetch, add, update, and delete publications that meet given criteria.
 * Also includes methods to get a list of all faculty and keywords in the db
 * @author Team 17: Fareed Abolhassani, Abdulaziz Alshkrah, Craig Price, Cynthia Zachar
 */
public class PublicationManager{
   private ResearchDb db;
   
   /**
    * Gets a ResearchDb instance
    */
   public PublicationManager(){
      db = ResearchDb.getInstance();
   }
   
   /**
    * Retrieves the names of all faculty from the db
    * @return an ArrayList of faculty full names
    */
   public ArrayList<String> getAllFaculty(){
      String query = "SELECT fName, lName FROM faculty";
      ArrayList<String> faculty = new ArrayList<String>();
      try{
         ArrayList<ArrayList<String>> data = db.getData(query, new ArrayList<String>(), false);
         for(ArrayList<String> row : data){
            faculty.add(row.get(0) + " " + row.get(1));
         }
      }
      catch(DLException dle){/*will return empty ArrayList*/}
      return faculty;
   }
   
   /**
    * Retrieves all paper titles from the db
    * @return an ArrayList of all paper titles
    */
   public ArrayList<String> getAllTitles(){
      String query = "SELECT title FROM papers";
      ArrayList<String> titles = new ArrayList<String>();
      try{
         ArrayList<ArrayList<String>> data = db.getData(query, new ArrayList<String>(), false);
         for(ArrayList<String> row : data){
            titles.add(row.get(0));
         }
      }
      catch(DLException dle){/*will return empty ArrayList*/}
      return titles;
   }
   
   /**
    * Retrieves all keywords from the db
    * @return an ArrayList of all unique keywords
    */
   public ArrayList<String> getAllKeywords(){
      String query = "SELECT DISTINCT keyword FROM paper_keywords";
      ArrayList<String> keywords = new ArrayList<String>();
      try{
         ArrayList<ArrayList<String>> data = db.getData(query, new ArrayList<String>(), false);
         for(ArrayList<String> row : data){
            keywords.add(row.get(0));
         }
      }
      catch(DLException dle){/*will return empty ArrayList*/}
      return keywords;
   }
   
   /**
    * Returns publication objects for papers containing the given title string
    * @param title    the title to search papers for
    * @return an ArrayList of Publications that contain the given string in their title
    */
   public ArrayList<Publication> fetchPublicationsByTitle(String title){
      String query = "SELECT papers.id, title, abstract, citation FROM papers "
                        + "WHERE title LIKE CONCAT('%',?,'%');";
      return fetchPublications(query, getParamArrayList(title));
   }
   
   /**
    * Returns publication objects for papers written by the given faculty member
    * @param name    the name of the faculty member to find papers for
    * @return an ArrayList of Publications written by the faculty member
    */
   public ArrayList<Publication> fetchPublicationsByFaculty(String name){
      String query = "SELECT papers.id, title, abstract, citation FROM papers "
                        + "JOIN authorship ON authorship.paperId = papers.id "
                        + "JOIN faculty ON faculty.id = authorship.facultyId "
                        + "WHERE fName = ? OR lName = ? OR CONCAT(fName,' ', lName)= ?;";
      return fetchPublications(query, getParamArrayList(name,name,name));
   }
   
   /**
    * Returns publication objects for papers with the given keyword
    * @param keyword    the keyword to find papers for
    * @return an ArrayList of Publications with the given keyword
    */
   public ArrayList<Publication> fetchPublicationsByKeyword(String keyword){
      String query = "SELECT papers.id, title, abstract, citation FROM papers "
                        + "JOIN paper_keywords ON paper_keywords.id = papers.id "
                        + "WHERE keyword = ?;";
      return fetchPublications(query, getParamArrayList(keyword));
   }
   
   /**
    * Returns publication objects for all papers in the db
    * @return an ArrayList of all Publications
    */
   public ArrayList<Publication> fetchAllPublications(){
      String query = "SELECT papers.id, title, abstract, citation FROM papers "
                        + "ORDER BY title";
      return fetchPublications(query, new ArrayList<String>());
   }
   
   /**
    * Adds a new publication to the database
    * @param paper   the new publication to add
    * @return the id of the new publication or -1 if operation was unsuccessful
    */
   public boolean addPublication(Publication paper){
      int lastId = -1;
      boolean success = true;
      String sql = "INSERT INTO papers (title,abstract,citation) VALUES (?,?,?);";
      
      try{
         db.startTrans();
         
         //add new record to papers
         lastId = db.insertData(sql, getParamArrayList(paper.getTitle(), paper.getAbstract(), paper.getCitation()));
      
         //add keyword & author records to paper_keywords & authorship tables
         if(lastId <= 0 || !insertKeywords(lastId, paper.getKeywords()) || !insertAuthors(lastId, paper.getAuthors())){
            success = false;
         }
      }
      catch(DLException dle){
         success = false;
      }

      try{
         //if successful, commit
         if(success){
            paper.setId(lastId);
            db.commitTrans();
         }
         //if unsuccessful, rollback
         else{
            db.rollbackTrans();
         }
         
         //end transaction and close connection
         db.endTrans();
         db.close();
      }
      catch(DLException dle){
         return false;
      }
      
      return success;
   }  //end addPublication
   
   /**
    * Updates a publication in the database
    * @param paper   the publication to update
    * @return true if the operation was successful; false otherwise
    */
   public boolean updatePublication(Publication paper){
      boolean success = true;
      String sql = "UPDATE papers SET title = ?, abstract = ?, citation = ? WHERE id = ?;";
      
      try{
         db.startTrans();
         
         //update record in papers
         int id = paper.getId();
         success = db.setData(sql, getParamArrayList(paper.getTitle(), paper.getAbstract(), paper.getCitation(), ""+id));
         //delete old paper_keywords and authorship records for id
         if(success && !deleteKeywords(id) && !deleteAuthors(id)){
            success = false;
         }
         //add updated keyword and author records
         if(success && !insertKeywords(id, paper.getKeywords()) && !insertAuthors(id, paper.getAuthors())){
            success = false;
         }
      }
      catch(DLException dle){
         success = false;
      }

      try{
         //if successful, commit
         if(success){
            db.commitTrans();
         }
         //if unsuccessful, rollback
         else{
            db.rollbackTrans();
         }
         
         //end transaction and close connection
         db.endTrans();
         db.close();
      }
      catch(DLException dle){
         return false;
      }
      
      return success;
   }  //end updatePublication
   
   /**
    * Removes a publication from the database
    * @param id   the id of the publication to remove
    * @return true if the operation was successful; false otherwise
    */
   public boolean removePublication(int id){
      boolean success = true;
      
      try{
         db.startTrans();
         
         //delete old paper_keywords and authorship records for id
         //not considered unsuccessful if there are no keywords or authors
         deleteKeywords(id);
         deleteAuthors(id);
         
         //remove paper from papers table
         String sql = "DELETE FROM papers WHERE id = ?;";
         success = db.setData(sql, getParamArrayList(""+id));
      }
      catch(DLException dle){
         success = false;
      }

      try{
         //if successful, commit
         if(success){
            db.commitTrans();
         }
         //if unsuccessful, rollback
         else{
            db.rollbackTrans();
         }
         
         //end transaction and close connection
         db.endTrans();
         db.close();
      }
      catch(DLException dle){
         return false;
      }
      
      return success;
   }
   
   /**
    * Executes a parameterized query and creates Publication objects from the retrieved data
    * @param query   the query to execute
    * @param values  the values to bind to the parameterized query
    * @return an ArrayList of Publications
    */
   private ArrayList<Publication> fetchPublications(String query, ArrayList<String> values){
      ArrayList<Publication> papers = null;
      try{
         db.startTrans();
         
         //get data for query
         ArrayList<ArrayList<String>> matches = db.getData(query, values, false);
         papers = new ArrayList<Publication>();
         
         //create publication objects from result
         for(ArrayList<String> match : matches){
            //get id, title, abstract, & citation
            int id = Integer.parseInt(match.get(0));
            String title = match.get(1);
            String paperAbstract = match.get(2);
            String citation = match.get(3);
            
            //execute query for keywords
            String keywordQuery = "SELECT keyword FROM paper_keywords "
                                    + "JOIN papers ON papers.id = paper_keywords.id "
                                    + "WHERE papers.id = ?;";
            ArrayList<ArrayList<String>> keywordResult = db.getData(keywordQuery, getParamArrayList(""+id), false);
            
            //get keywords
            ArrayList<String> keywords = new ArrayList<String>();
            for(ArrayList<String> keywordRow : keywordResult){
               keywords.add(keywordRow.get(0));
            }
            
            //execute query for authors
            String authorQuery = "SELECT CONCAT(fName,' ',lName) FROM faculty "
                                    + "JOIN authorship ON authorship.facultyId = faculty.id "
                                    + "JOIN papers ON papers.id = authorship.paperId "
                                    + "WHERE papers.id = ?;";
            ArrayList<ArrayList<String>> authorResult = db.getData(authorQuery, getParamArrayList(""+id), false);
            
            //get authors
            ArrayList<String> authors = new ArrayList<String>();
            for(ArrayList<String> authorRow : authorResult){
               authors.add(authorRow.get(0));
            }
            
            //add new publication to list
            papers.add(new Publication(id, title, paperAbstract, citation, keywords, authors));
         }
      }
      catch(DLException dle){
         papers = null;
      }
      finally{
         try{
            //end transaction and close connection
            db.endTrans();
            db.close();
         }
         catch(DLException dle){/*probably shouldn't return null if ending transaction/close fails*/}
      }
      
      return papers;
   }  //end fetchPublications method
   
   /**
    * Inserts new keyword records into the paper_keywords table
    * @param id         the id of the paper that keywords apply to
    * @param keywords   the keywords to add for the paper
    * @return true if insertion is successful; false otherwise
    */
   private boolean insertKeywords(int id, ArrayList<String> keywords) throws DLException{
      for(String keyword : keywords){
         String sql = "INSERT INTO paper_keywords (id, keyword) VALUES (?,?);";
         if(!db.setData(sql, getParamArrayList(""+id, keyword))){
            return false;
         }
      }
      return true;
   }
   
   /**
    * Inserts new author records into the authorship table
    * Note that this method should only be used in a transaction
    * @param id         the id of the paper that authors contributed to
    * @param authors   the authors to add for the paper
    * @return true if insertion is successful; false otherwise
    */
   private boolean insertAuthors(int id, ArrayList<String> authors) throws DLException{
      for(String author : authors){
      
         //get the id of the faculty author
         String[] splitName = author.split(" ");
         String query = "SELECT id FROM faculty WHERE fName = ? AND lName = ?;";
         ArrayList<ArrayList<String>> facultyResult = db.getData(query, getParamArrayList(splitName[0],splitName[1]), false);
         
         //if author cannot be found
         if(facultyResult == null || facultyResult.size() < 1){
            return false;
         }
         //if author found, insert record into authorship
         else{
            String sql = "INSERT INTO authorship (facultyId, paperId) VALUES (?,?)";
            if(!db.setData(sql, getParamArrayList(facultyResult.get(0).get(0), ""+id))){
               return false;
            }
         }  
      }
      return true;
   }
   
   /**
    * Deletes keywords for a particular paper id
    * @param id   the id of the paper to delete keywords for
    * @return true if deletion is successful; false otherwise
    */
   private boolean deleteKeywords(int id) throws DLException{
      return db.setData("DELETE FROM paper_keywords WHERE id = ?", getParamArrayList(""+id));
   }
   
   /**
    * Deletes authors for a particular paper id
    * @param id   the id of the paper to delete authors for
    * @return true if deletion is successful; false otherwise
    */
   private boolean deleteAuthors(int id) throws DLException{
      return db.setData("DELETE FROM authorship WHERE paperId = ?", getParamArrayList(""+id));
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

}  //end PublicationManager class