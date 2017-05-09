import java.util.*;        //for ArrayList
import javax.swing.*;      //for JComponents
import java.awt.*;         //for Cursor
import java.awt.event.*;   //for MouseAdapter

/**
 * A window that allows the user to either add or edit a new publication
 * @author Team 17: Fareed Abolhassani, Abdulaziz Alshkrah, Craig Price, Cynthia Zachar
 */
public class AddEditWindow{
   private FacultyManager fManager = new FacultyManager();
   private final int LABEL_WIDTH = 80;
   private final int LABEL_HEIGHT = 20;
   private final int WINDOW_HEIGHT = 300;
   private final int WINDOW_WIDTH = 500;
   private Publication paper = null;
   private Faculty user = null;
   
   /**
    * Presents text fields for the user to edit a paper's current details 
    * @param isEdit           true if the operation to be performed is an edit; false if it's an add
    * @param facView          the faculty view window from which the user selected to perform an add or edit
    * @param paperOrFaculty   if the operation is an edit, this will be the publication to be edited; if it is add, this will be the faculty member who is logged in
    */
   public AddEditWindow(boolean isEdit, FacultyView facView, Object paperOrFaculty){
      //if edit, initialize publication
      if(isEdit){
         paper = (Publication)paperOrFaculty;
      }
      //if add, initialize faculty user
      else{
         user = (Faculty)paperOrFaculty;
      }
      
      JFrame frame = new JFrame();
         frame.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
         frame.setTitle((isEdit ? "Edit" : "Add") + " Publication");

         //title
         frame.add(new JLabel((isEdit ? " Edit" : " Add") + " Publication:"), BorderLayout.NORTH);
         
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
               JTextField jtfTitle = new JTextField(isEdit ? paper.getTitle() : "");
               jpTextFields.add(jtfTitle);
               
               //authors
               JTextField jtfAuthors = new JTextField(isEdit ? getCommaList(paper.getAuthors()) : "");
               jpTextFields.add(jtfAuthors);
               
               //keywords
               JTextField jtfKeywords = new JTextField(isEdit ? getCommaList(paper.getKeywords()) : "");
               jpTextFields.add(jtfKeywords);
            jpAllFields.add(jpTextFields, BorderLayout.NORTH);
            
            //all text areas
            JPanel jpTextAreas = new JPanel(new BorderLayout());
               //abstract
               JTextArea jtaAbstract = getFieldTextArea(isEdit ? paper.getAbstract() : "");
               jpTextAreas.add(new JScrollPane(jtaAbstract),BorderLayout.CENTER);
               
               //citation
               JTextArea jtaCitation = getFieldTextArea(isEdit ? paper.getCitation() : "");
               jpTextAreas.add(new JScrollPane(jtaCitation),BorderLayout.SOUTH);
            jpAllFields.add(jpTextAreas, BorderLayout.CENTER);
         frame.add(jpAllFields, BorderLayout.CENTER);
         
         //all buttons at bottom of window
         JPanel jpControls = new JPanel();
            //add or save button
            JButton jbAction = new JButton(isEdit ? "Save" : "Add");
               jbAction.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent ae){
                     String title = jtfTitle.getText();
                     String _abstract = jtaAbstract.getText();
                     String citation = jtaCitation.getText();
                     ArrayList<String> keywords = getArrayListFromCommaList(jtfKeywords.getText());
                     ArrayList<String> authors = getArrayListFromCommaList(jtfAuthors.getText());
                     String status = "";
                     
                     //attempt update
                     if(isEdit){
                        status = fManager.editPublication(paper.getId(), title, _abstract, citation, keywords, authors);
                     }
                     //attempt add
                     else{
                        status = fManager.addPublication(title, _abstract, citation, keywords, authors);
                     }
                     
                     //if operation unsuccessful
                     if(status.substring(0,5).equals("Error")){
                        JOptionPane.showMessageDialog(null, status, "Error", JOptionPane.ERROR_MESSAGE);
                     }
                     //if operation successful
                     else{
                        JOptionPane.showMessageDialog(null, status, (isEdit ? "Update" : "Publication Addition") + " Successful", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                        facView.reloadTable();
                     }
                  }
               });
            jpControls.add(jbAction);
            
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
   }  //end AddEditWindow constructor
   
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

}  //end AddEditWindow class