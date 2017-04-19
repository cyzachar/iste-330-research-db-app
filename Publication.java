import java.util.*;  //for ArrayList   

/**
 * Represents a publication record with an id, title, abstract, citation, keywords, and authors
 * @author Team 17
 */
public class Publication{
   private int id;
   private String title;
   private String paperAbstract;
   private String citation;
   private ArrayList<String> keywords;
   private ArrayList<String> authors;
   
   /**
    * Initializes all attributes except id
    * @param title      the title of the publication
    * @param _abstract  the abstract of the publication
    * @param citation   the citation of the publication
    * @param keywords   the keywords of the publication
    * @param authors    the authors of the publication
    */
   public Publication(String _title, String _abstract, String _citation, ArrayList<String> _keywords, ArrayList<String> _authors){
      title = _title;
      paperAbstract = _abstract;
      citation = _citation;
      keywords = _keywords;
      authors = _authors;
   }
   
   /**
    * Initializes all attributes
    * @param id         the id of the publication
    * @param title      the title of the publication
    * @param _abstract  the abstract of the publication
    * @param citation   the citation of the publication
    * @param keywords   the keywords of the publication
    * @param authors    the authors of the publication
    */
   public Publication(int _id, String _title, String _abstract, String _citation, ArrayList<String> _keywords, ArrayList<String> _authors){
      id = _id;
      title = _title;
      paperAbstract = _abstract;
      citation = _citation;
      keywords = _keywords;
      authors = _authors;
   }
   
   /**
    * Mutator for id
    * @param _id   the new publication id
    */
   public void setId(int _id){
      id = _id;
   }
   
   /**
    * Mutator for title
    * @param _title   the new publication title
    */
   public void setTitle(String _title){
      title = _title;
   }
   
   /**
    * Mutator for paperAbstract
    * @param _abstract   the new publication abstract
    */
   public void setAbstract(String _abstract){
      paperAbstract = _abstract;
   }
   
   /**
    * Mutator for citation
    * @param _citation   the new publication citation
    */
   public void setCitation(String _citation){
      citation = _citation;
   }
   
   /**
    * Mutator for keywords
    * @param _keywords   the new keywords for the publication
    */
   public void setKeywords(ArrayList<String> _keywords){
      keywords = _keywords;
   }
   
   /**
    * Mutator for authors
    * @param _authors   the new authors for the publication
    */
   public void setAuthors(ArrayList<String> _authors){
      authors = _authors;
   }
   
   /**
    * Accessor for id
    * @return the publication id
    */
   public int getId(){
      return id;
   }
   
   /**
    * Accessor for title
    * @return the publication title
    */
   public String getTitle(){
      return title;
   }
   
   /**
    * Accessor for abstract
    * @return the publication abstract
    */
   public String getAbstract(){
      return paperAbstract;
   }
   
   /**
    * Accessor for citation
    * @return the publication citation
    */
   public String getCitation(){
      return citation;
   }
   
   /**
    * Accessor for keywords
    * @return the publication keywords
    */
   public ArrayList<String> getKeywords(){
      return keywords;
   }
   
   /**
    * Accessor for authors
    * @return the publication authors
    */
   public ArrayList<String> getAuthors(){
      return authors;
   }
   
   /**
    * Returns a string describing publication details
    * FOR TESTING PURPOSES ONLY
    * @return a string describing publication id, title, keywords, & authors
    */
   public String toString(){
      String str = "Id: " + id + "\nTitle: " + title + "\nKeywords: ";
      for(String keyword : keywords){
         str += keyword + ",";
      }
      str = str.substring(0, str.length()-1) + "\nAuthors: ";
      for(String author: authors){
         str += author + ",";
      }
      return str.substring(0, str.length()-1) + "\n";
   }
   
}  //end Publication class