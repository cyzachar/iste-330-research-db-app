import java.util.*;        //for ArrayList
import javax.swing.*;      //for JPanel
import java.awt.*;         //for Cursor
import java.awt.event.*;   //for MouseAdapter

/**
 * Displays a publication title and allows the user to edit and delete that publication
 * @author Team 17: Fareed Abolhassani, Abdulaziz Alshkrah, Craig Price, Cynthia Zachar
 */
public class FacultyPublicationRow extends JPanel{
   private FacultyManager manager = new FacultyManager();
   private Publication paper;
   private String authors = "";
   private final int TITLE_WIDTH = 100;
   private final int BUTTON_WIDTH = 50;
   private final int ROW_HEIGHT = 20;
   private final int FONT_SIZE = 14;
   
   //main is only for testing purposes
   public static void main(String[] args){
      JFrame jfMain = new JFrame();
      jfMain.add(new FacultyPublicationRow(new Publication(21,"A title!","An abstract","A citation",getParamArrayList("Basket Weaving","Nonsense"),getParamArrayList("Steve Zilora","Dan Bogaard"))),BorderLayout.CENTER);
      jfMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jfMain.setLocationRelativeTo(null);
      jfMain.pack();
      jfMain.setVisible(true);
   }
   
   /**
    * Creates a row panel displaying a paper's authors along with an edit & delete button
    * @param _paper  the paper to display the title of
    */
   public FacultyPublicationRow(Publication _paper){
      paper = _paper;
      Font rowFont = new Font("rowFont", Font.PLAIN, FONT_SIZE);
      
      //paper title
      String title = paper.getTitle();
      JLabel jlTitle = new JLabel(paper.getTitle());
      jlTitle.setPreferredSize(new Dimension(TITLE_WIDTH, ROW_HEIGHT));
      jlTitle.setFont(rowFont);
      add(jlTitle);
      
      //edit button
      JButton jbEdit = new JButton("Edit");
      jbEdit.addActionListener(new ActionListener(){
         //displays edit window
         public void actionPerformed(ActionEvent ae){
            new EditWindow();
         }
      });
      add(jbEdit);
      
      //delete button
      JButton jbDelete = new JButton("Delete");
      jbDelete.addActionListener(new ActionListener(){
         //confirms & performs delete operation
         public void actionPerformed(ActionEvent ae){
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this publication?", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            //if user confirms deletion
            if(choice == JOptionPane.YES_OPTION){
               //deletion successful
               if(manager.deletePublication(paper.getId())){
                  JOptionPane.showMessageDialog(null, "Deletion was successful", "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
               }
               //deletion unsuccessful
               else{
                  JOptionPane.showMessageDialog(null, "Deletion was unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
               }
               
            }
         }
      });
      add(jbDelete);
      
   }  //end FacultyPublicationRow constructor
   
   //for testing only
   private static ArrayList<String> getParamArrayList(String... params){
      ArrayList<String> paramList = new ArrayList<String>();
      for(String param : params){
         paramList.add(param);
      }
      return paramList;
   }
   
   /**
    * A window that allows the user to edit publication details
    */
   class EditWindow{
      private final int LABEL_WIDTH = 80;
      private final int LABEL_HEIGHT = 20;
      
      /**
       * Presents text fields for the user to edit a paper's current details 
       */
      public EditWindow(){
         JFrame frame = new JFrame();
         frame.setTitle("Edit Publication");

         //title
         frame.add(new JLabel(" Edit Publication:"), BorderLayout.NORTH);
         
         //all field labels on left of window
         JPanel jpLabels = new JPanel(new BorderLayout());
            //the labels for text fields
            JPanel jpUpperLabels = new JPanel(new GridLayout(0,1));
               jpUpperLabels.add(getFieldLabel(" Title: "));
               jpUpperLabels.add(getFieldLabel(" Authors: "));
               jpUpperLabels.add(getFieldLabel(" Keywords: "));
            jpLabels.add(jpUpperLabels, BorderLayout.NORTH);
            
            //the labels for text areas
            JPanel jpLowerLabels = new JPanel(new GridLayout(0,1));
               jpLowerLabels.add(getFieldLabel(" Abstract: "));
               jpLowerLabels.add(getFieldLabel(" Citation: "));
            jpLabels.add(jpLowerLabels, BorderLayout.CENTER);
         frame.add(jpLabels, BorderLayout.WEST);
         
         //all fields at the right of window
         JPanel jpAllFields = new JPanel(new BorderLayout());
            //all text fields
            JPanel jpTextFields = new JPanel(new GridLayout(0,1));
               //title
               JTextField jtfTitle = new JTextField(paper.getTitle());
               jpTextFields.add(jtfTitle);
               
               //authors
               JTextField jtfAuthors = new JTextField(getCommaList(paper.getAuthors()));
               jpTextFields.add(jtfAuthors);
               
               //keywords
               JTextField jtfKeywords = new JTextField(getCommaList(paper.getKeywords()));
               jpTextFields.add(jtfKeywords);
            jpAllFields.add(jpTextFields, BorderLayout.NORTH);
            
            //all text areas
            JPanel jpTextAreas = new JPanel(new GridLayout(0,1));
               //abstract
               JTextArea jtaAbstract = getFieldTextArea(paper.getAbstract());
               jpTextAreas.add(new JScrollPane(jtaAbstract));
               
               //citation
               JTextArea jtaCitation = getFieldTextArea(paper.getCitation());
               jpTextAreas.add(new JScrollPane(jtaCitation));
            jpAllFields.add(jpTextAreas, BorderLayout.CENTER);
         frame.add(jpAllFields, BorderLayout.CENTER);
         
         //all buttons at bottom of window
         JPanel jpControls = new JPanel();
            //save button
            JButton jbSave = new JButton("Save");
               jbSave.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent ae){
                     //attempt update
                     String status = manager.editPublication(paper.getId(), 
                                                             jtfTitle.getText(), 
                                                             jtaAbstract.getText(), 
                                                             jtaCitation.getText(), 
                                                             getArrayListFromCommaList(jtfKeywords.getText()), 
                                                             getArrayListFromCommaList(jtfAuthors.getText()) );
                     //if update unsuccessful
                     if(status.substring(0,5).equals("Error")){
                        JOptionPane.showMessageDialog(null, status, "Error", JOptionPane.ERROR_MESSAGE);
                     }
                     //if update successful
                     else{
                        JOptionPane.showMessageDialog(null, status, "Update Successful", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                     }
                  }
               });
            jpControls.add(jbSave);
            
            //cancel button
            JButton jbCancel = new JButton("Cancel");
               jbCancel.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent ae){
                     frame.dispose();
                  }
               });
            jpControls.add(jbCancel);
         frame.add(jpControls, BorderLayout.SOUTH);
         
         //handle position and display of frame
         frame.pack();
         frame.setLocationRelativeTo(null);
         frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         frame.setVisible(true);
      }  //end EditWindow constructor
      
      /**
       * Creates a label of a certain size
       * @param text    the text for the label
       * @return a label of a certain size
       */
      private JLabel getFieldLabel(String text){
         JLabel label = new JLabel(text);
         label.setPreferredSize(new Dimension(LABEL_WIDTH,LABEL_HEIGHT));
         return label;
      }
      
      /**
       * Creates a text area with wrapped text
       * @param text    the text to show in the text area
       * @return a text area with wrapped text
       */
      private JTextArea getFieldTextArea(String text){
         JTextArea textArea = new JTextArea(text);
         textArea.setLineWrap(true);
         textArea.setWrapStyleWord(true);
         return textArea;
      }
      
      /**
       * Creates a String of comma separated values from an ArrayList
       * @param list    the ArrayList to turn into a string
       * @return a string of comma separated values
       */
      private String getCommaList(ArrayList<String> list){
         String str = "";
         for(String item : list){
            str += item + ",";
         }
         return str.substring(0,str.length()-1);
      }
      
      /**
       * Creates an ArrayList from a String of comma separated values
       * @param commaList    a string of comma separated values
       * @return an ArrayList of values
       */
      private ArrayList<String> getArrayListFromCommaList(String commaList){
         String[] splitList = commaList.split(",");
         ArrayList<String> items = new ArrayList<String>();
         for(String item : splitList){
            items.add(item.trim());
         }
         return items;
      }
   
   }  //end EditWindow inner class

}  //end FacultyPublicationRow class