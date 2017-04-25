import java.util.*;        //for ArrayList
import javax.swing.*;      //for JPanel
import java.awt.*;         //for Cursor
import java.awt.event.*;   //for MouseAdapter

public class PublicPublicationRow extends JPanel{
   private Publication paper;
   private String authors = "";
   private final int TITLE_WIDTH = 100;
   private final int AUTHORS_WIDTH = 300;
   private final int LABEL_HEIGHT = 20;
   private final int FONT_SIZE = 14;
   
   //main is only for testing purposes
   public static void main(String[] args){
      JFrame jfMain = new JFrame();
      jfMain.add(new PublicPublicationRow(new Publication("A title!","An abstract","A citation",getParamArrayList("Basket Weaving","Nonsense"),getParamArrayList("Steve Zilora","Dan Bogaard"))),BorderLayout.CENTER);
      jfMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jfMain.setLocationRelativeTo(null);
      jfMain.pack();
      jfMain.setVisible(true);
   }
   
   public PublicPublicationRow(Publication _paper){
      paper = _paper;
      Font rowFont = new Font("rowFont", Font.PLAIN, FONT_SIZE);
      
      String title = paper.getTitle();
      JLabel jlTitle = new JLabel(paper.getTitle());
      jlTitle.setPreferredSize(new Dimension(TITLE_WIDTH, LABEL_HEIGHT));
      jlTitle.setFont(rowFont);
      add(jlTitle);
      
      add(new JLabel("|"));
      
      for(String author : paper.getAuthors()){
         authors += author + ",";
      }
      JLabel jlAuthors = new JLabel(authors.substring(0, authors.length() - 1), SwingConstants.LEFT);
      jlAuthors.setPreferredSize(new Dimension(AUTHORS_WIDTH, LABEL_HEIGHT));
      jlAuthors.setFont(rowFont);
      add(jlAuthors);
      
      addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent me){
            new PublicationDetails(title, authors, paper.getAbstract(), paper.getCitation());
         }
         
         public void mouseEntered(MouseEvent me){
            setCursor(new Cursor(Cursor.HAND_CURSOR));
         }
         
         public void mouseExited(MouseEvent me){
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }
      });
   }
   
   //for testing only
   private static ArrayList<String> getParamArrayList(String... params){
      ArrayList<String> paramList = new ArrayList<String>();
      for(String param : params){
         paramList.add(param);
      }
      return paramList;
   }

}