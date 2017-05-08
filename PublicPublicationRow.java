import java.util.*;        //for ArrayList
import javax.swing.*;      //for JPanel
import java.awt.*;         //for Cursor
import java.awt.event.*;   //for MouseAdapter

/**
 * Displays paper title and authors and shows details when clicked
 * @author Team 17: Fareed Abolhassani, Abdulaziz Alshkrah, Craig Price, Cynthia Zachar
 */
public class PublicPublicationRow extends JPanel{
   private Publication paper;
   private String authors = "";
   private final int TITLE_WIDTH = 400;
   private final int AUTHORS_WIDTH = 240;
   private final int LABEL_HEIGHT = 20;
   private final int FONT_SIZE = 14;
   
   //main is only for testing purposes
   public static void main(String[] args){
      JFrame jfMain = new JFrame();
      jfMain.add(new PublicPublicationRow(new Publication("A title!","An abstract An abstract An abstract An abstract An abstract An abstract An abstract An abstract An abstract An abstract An abstract An abstract An abstract An abstract An abstract An abstract An abstract An abstract An abstract An abstract","A citation A citation A citation A citation A citation A citation A citation A citation A citation A citation A citation",getParamArrayList("Basket Weaving","Nonsense"),getParamArrayList("Steve Zilora","Dan Bogaard"))),BorderLayout.CENTER);
      jfMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jfMain.setLocationRelativeTo(null);
      jfMain.pack();
      jfMain.setVisible(true);
   }
   
   /**
    * Displays paper title and authors and shows details when clicked
    * @param _paper  the publication being displayed
    */
   public PublicPublicationRow(Publication _paper){
      paper = _paper;
      Font rowFont = new Font("rowFont", Font.PLAIN, FONT_SIZE);
      
      //paper title
      String title = paper.getTitle();
      JLabel jlTitle = new JLabel(paper.getTitle());
      jlTitle.setPreferredSize(new Dimension(TITLE_WIDTH, LABEL_HEIGHT));
      jlTitle.setFont(rowFont);
      jlTitle.setToolTipText(title);
      addRowMouseListener(jlTitle);
      add(jlTitle);
      
      //divider
      add(new JLabel("|"));
      
      //paper authors
      authors = getCommaList(paper.getAuthors());
      JLabel jlAuthors = new JLabel(authors, SwingConstants.LEFT);
      jlAuthors.setPreferredSize(new Dimension(AUTHORS_WIDTH, LABEL_HEIGHT));
      jlAuthors.setFont(rowFont);
      add(jlAuthors);
      
      //mouselistener for row
      
      addRowMouseListener(this);
      
   }  //end PublicPublicationRow constructor 
  
   /**
    * Displays publication details in a new window
    */
   class PublicationDetails extends JFrame{
   
      /**
       * Displays a publication's title, authors, keywords, abstract, & citation
       */
      public PublicationDetails(){
         //window appearance
         setTitle(paper.getTitle());
         setMinimumSize(new Dimension(500,250));
         
         JPanel jpTopInfo = new JPanel(new GridLayout(0,1));
            //title
            JLabel jlTitle = new JLabel(paper.getTitle(), SwingConstants.CENTER);
               Font titleFont = new Font("titleFont", Font.PLAIN, 30);
               jlTitle.setFont(titleFont);
            jpTopInfo.add(jlTitle);

            
            //authors
            JLabel jlAuthors = new JLabel(authors, SwingConstants.CENTER);
               Font authorsFont = new Font("authorsFont", Font.PLAIN, 25);
               jlTitle.setFont(authorsFont);
            jpTopInfo.add(jlAuthors);
            
            //keywords
            jpTopInfo.add(new JLabel("Keywords: " + getCommaList(paper.getKeywords())));
         add(jpTopInfo, BorderLayout.NORTH);
         
         //abstract & citation
         JPanel jpLowerInfo = new JPanel(new GridLayout(0,1));
            jpLowerInfo.add(getTextBlock("Abstract: " + paper.getAbstract(),true));
            jpLowerInfo.add(getTextBlock("Citation: " + paper.getCitation(),false));
         add(jpLowerInfo, BorderLayout.CENTER);
         
         //window appearance and location
         pack();
         setLocationRelativeTo(null);
         setDefaultCloseOperation(DISPOSE_ON_CLOSE);
         setVisible(true);
         
      }  //end PublicationDetails constructor

    }  //end PublicationDetails class
    
    //TO DO: ContactForm class
    class ContactForm extends JFrame{
      //instantiate SpeakingRequestManager
      
      public ContactForm(){
         //window header
         
         //window instructions
         
         //requester name field
         
         //requester email field
         
         //requester phone field
         
         //author to contact checkboxes (use this row's publication obj to get authors)
         
         //message text area
         
         //send button
            
         //ActionListener for send button
         
            //check that name, email, author, and msg were included (phone is optional)
         
            //validate phone number format if included
         
            //strip out any non-numeric characters from phone# so it is simply a 10 digit number
            
            //call SpeakingRequestManager's appropriate addRequest method
      }
      
    }
    
    /**
     * Creates a String of comma separated values from an ArrayList
     * @param list    the ArrayList to turn into a string
     * @return a string of comma separated values
     */
   private String getCommaList(ArrayList<String> list){
      if(list.size() == 0){
         return "";
      }
      
      String str = "";
      for(String item : list){
         str += item + ",";
      }
      
      return str.substring(0,str.length()-1);
   }
   
   //for testing only
   private static ArrayList<String> getParamArrayList(String... params){
      ArrayList<String> paramList = new ArrayList<String>();
      for(String param : params){
         paramList.add(param);
      }
      return paramList;
   }
   
   /**
    * Creates a non-editable text area with wrapped text
    * @param text    the text to show in the text area
    * @return a text area with wrapped text
    */
   private JComponent getTextBlock(String text, boolean isScrollable){
      JTextArea block = new JTextArea(text);
      block.setLineWrap(true);
      block.setWrapStyleWord(true);
      block.setEditable(false);
      block.setBackground(null);
      if(isScrollable){
         return new JScrollPane(block);
      }
      return block;
   }
   
   /**
    * Adds a mouse listener to a component, which displays publication details when
    * mouse is clicked and sets the cursor to indicate that an area is clickable
    * @param element    the component to add the MouseListener to
    */
   private void addRowMouseListener(Component element){
      element.addMouseListener(new MouseAdapter(){
      
         /*Displays publication details*/
         public void mouseClicked(MouseEvent me){
            new PublicationDetails();
         }
         
         /*Switches cursor to a hand when it's over the row*/
         public void mouseEntered(MouseEvent me){
            setCursor(new Cursor(Cursor.HAND_CURSOR));
         }
         
         /*Switches cursor back to default when it's not over the row*/
         public void mouseExited(MouseEvent me){
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }
      });
   }

}  //end PublicPublicationRow class