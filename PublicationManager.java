import java.util.*;  //for ArrayList

public class PublicationManager{
   private ResearchDb db;
   
   public PublicationManager(){
      db = ResearchDb.getInstance();
   }
   
   /**
    * 
    */
   public ArrayList<Publication> fetchPublicationsByFaculty(String name){
      String query = "SELECT papers.id, title, abstract, citation FROM papers "
                        + "JOIN authorship ON authorship.paperId = papers.id "
                        + "JOIN faculty ON faculty.id = authorship.facultyId "
                        + "WHERE fName = ? OR lName = ? OR CONCAT(fName,' ', lName)= ?;";
      return fetchPublications(query, getParamArrayList(name,name,name));
   }
   
   /**
    * 
    */
   public ArrayList<Publication> fetchPublicationsByKeyword(String keyword){
      String query = "SELECT papers.id, title, abstract, citation FROM papers "
                        + "JOIN paper_keywords ON paper_keywords.id = papers.id "
                        + "WHERE keyword = ?;";
      return fetchPublications(query, getParamArrayList(keyword));
   }
   
   
   private ArrayList<Publication> fetchPublications(String query, ArrayList<String> values){
      ArrayList<Publication> papers = null;
      try{
         db.startTrans();
         ArrayList<ArrayList<String>> matches = db.getData(query, values, false);
         papers = new ArrayList<Publication>();
         
         for(ArrayList<String> match : matches){
            int id = Integer.parseInt(match.get(0));
            String title = match.get(1);
            String paperAbstract = match.get(2);
            String citation = match.get(3);
            
            String keywordQuery = "SELECT keyword FROM paper_keywords "
                                    + "JOIN papers ON papers.id = paper_keywords.id "
                                    + "WHERE papers.id = ?;";
            ArrayList<ArrayList<String>> keywordResult = db.getData(keywordQuery, getParamArrayList(""+id), false);
            
            ArrayList<String> keywords = new ArrayList<String>();
            for(ArrayList<String> keywordRow : keywordResult){
               keywords.add(keywordRow.get(0));
            }
            
            String authorQuery = "SELECT CONCAT(fName,' ',lName) FROM faculty "
                                    + "JOIN authorship ON authorship.facultyId = faculty.id "
                                    + "JOIN papers ON papers.id = authorship.paperId "
                                    + "WHERE papers.id = ?;";
            ArrayList<ArrayList<String>> authorResult = db.getData(authorQuery, getParamArrayList(""+id), false);
            
            ArrayList<String> authors = new ArrayList<String>();
            for(ArrayList<String> authorRow : authorResult){
               authors.add(authorRow.get(0));
            }
            
            papers.add(new Publication(id, title, paperAbstract, citation, keywords, authors));
         }
         
         //end transaction and close connection
         db.endTrans();
         db.close();
      }
      catch(DLException dle){
         return null;
      }
      return papers;
   }  //end fetchPublications
   
   
   /**
    * @return the id of the new publication or -1 if operation was unsuccessful
    */
    /*
   public int addPublication(Publication paper){
   
   }
   
   public boolean updatePublication(Publication paper){
   
   }
   
   public boolean removePublication(int id){
   
   }
   */
   
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

}