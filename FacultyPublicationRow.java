import java.util.*;        //for ArrayList
import javax.swing.*;      //for JPanel
import java.awt.*;         //for Cursor
import java.awt.event.*;   //for MouseAdapter

/**
 * Displays a publication title and allows the user to edit and delete that publication
 * @author Team 17: Fareed Abolhassani, Abdulaziz Alshkrah, Craig Price, Cynthia Zachar
 */
public class FacultyPublicationRow extends JPanel{
   private FacultyManager fManager = new FacultyManager();
   private FacultyView facView;
   private Publication paper;
   private String authors = "";
   private final int TITLE_WIDTH = 300;
   private final int BUTTON_WIDTH = 50;
   private final int ROW_HEIGHT = 20;
   private final int FONT_SIZE = 14;
   
   //main is only for testing purposes
   public static void main(String[] args){
      JFrame jfMain = new JFrame();
      jfMain.add(new FacultyPublicationRow(new Publication(21,"A title!","An abstract","A citation",getParamArrayList("Basket Weaving","Nonsense"),getParamArrayList("Steve Zilora","Dan Bogaard")),new FacultyView((new FacultyManager()).checkLogin("5f47859188a602594556580532e814a3","sjz@it.rit.edu"))),BorderLayout.CENTER);
      jfMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jfMain.setLocationRelativeTo(null);
      jfMain.pack();
      jfMain.setVisible(true);
   }
   
   /**
    * Creates a row panel displaying a paper's authors along with an edit & delete button
    * @param _paper  the paper to display the title of
    */
   public FacultyPublicationRow(Publication _paper, FacultyView _facView){
      paper = _paper;
      facView = _facView;
      Font rowFont = new Font("rowFont", Font.PLAIN, FONT_SIZE);
      
      //paper title
      String title = paper.getTitle();
      JLabel jlTitle = new JLabel(paper.getTitle());
      jlTitle.setPreferredSize(new Dimension(TITLE_WIDTH, ROW_HEIGHT));
      jlTitle.setFont(rowFont);
      jlTitle.setToolTipText(title);
      add(jlTitle);
      
      //edit button
      JButton jbEdit = new JButton("Edit");
      jbEdit.addActionListener(new ActionListener(){
         //displays edit window
         public void actionPerformed(ActionEvent ae){
            new AddEditWindow(true, facView, paper);
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
               if(fManager.deletePublication(paper.getId())){
                  JOptionPane.showMessageDialog(null, "Deletion was successful", "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
                  facView.reloadTable();
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

}  //end FacultyPublicationRow class