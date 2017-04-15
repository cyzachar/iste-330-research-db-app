import java.util.*;  //for ArrayList   

public class Publication{
   private int id;
   private String title;
   private String paperAbstract;
   private String citation;
   private ArrayList<String> keywords;
   private ArrayList<String> authors;
   
   public Publication(String _title, String _abstract, String _citation, ArrayList<String> _keywords, ArrayList<String> _authors){
      title = _title;
      paperAbstract = _abstract;
      citation = _citation;
      keywords = _keywords;
      authors = _authors;
   }
   
   public Publication(int _id, String _title, String _abstract, String _citation, ArrayList<String> _keywords, ArrayList<String> _authors){
      id = _id;
      title = _title;
      paperAbstract = _abstract;
      citation = _citation;
      keywords = _keywords;
      authors = _authors;
   }
   
   public void setTitle(String _title){
      title = _title;
   }
   
   public void setAbstract(String _abstract){
      paperAbstract = _abstract;
   }
   
   public void setCitation(String _citation){
      citation = _citation;
   }
   
   public void setKeywords(ArrayList<String> _keywords){
      keywords = _keywords;
   }
   
   public void setAuthors(ArrayList<String> _authors){
      authors = _authors;
   }
   
   public String getTitle(){
      return title;
   }
   
   public String getAbstract(){
      return paperAbstract;
   }
   
   public String getCitation(){
      return citation;
   }
   
   public ArrayList<String> getKeywords(){
      return keywords;
   }
   
   public ArrayList<String> getAuthors(){
      return authors;
   }
   
   //NOTE: The following is for testing purposes
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