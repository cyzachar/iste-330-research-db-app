import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Displays a window that faculty can, once logged in, use to view, add, update, and delete publications
 * @author Team 17: Fareed Abolhassani, Abdulaziz Alshkrah, Craig Price, Cynthia Zachar
 */
public class FacultyView{
   private Faculty user;
   private FacultyManager fManager = new FacultyManager();
   private SearchWindow mainWindow;
   private JPanel jpPapers;
   private final int TABLE_HEIGHT = 200;
   private final int TABLE_WIDTH = 470;
   
   //FOR TESTING ONLY
   public static void main(String[] args){
      new FacultyView((new FacultyManager()).checkLogin("5f47859188a602594556580532e814a3","sjz@it.rit.edu"),new SearchWindow());
   }
   
   /**
    * Displays a faculty member's publications and provides options to add, edit, and delete publications
    * @param _user         the faculty member who is logged in
    * @param _mainWindow   the main search screen that the user is presented with once logged out
    */
   public FacultyView(Faculty _user, SearchWindow _mainWindow){
      if(user == null){
         JOptionPane.showMessageDialog(null,"Could not connect to database","Connection Error",JOptionPane.ERROR_MESSAGE);
      }
      else{
         user = _user;
         mainWindow = _mainWindow;
         
         JFrame frame = new JFrame();
         frame.setTitle("RIT Research Database");
         
         //header, logout, and add button
         JPanel jpTop = new JPanel(new BorderLayout());
            //logout button
            JPanel jpLogout = new JPanel(new BorderLayout());
               JButton jbLogout = new JButton("Logout");
               jbLogout.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent ae){
                     frame.dispose();
                     mainWindow.show();
                  }
               });
               jpLogout.add(jbLogout, BorderLayout.EAST);
            jpTop.add(jpLogout, BorderLayout.NORTH);
            
            //headers
            JPanel jpHeaders = new JPanel(new GridLayout(0,1));
               JLabel jlHeader = new JLabel("RIT Research Database", JLabel.CENTER);
                  jlHeader.setFont(new Font("headerFont", Font.PLAIN, 32));
               jpHeaders.add(jlHeader);
               
               JLabel jlWelcome = new JLabel("Welcome, " + user.getFirstName() + " " + user.getLastName(), JLabel.CENTER);
                  jlWelcome.setFont(new Font("headerFont", Font.PLAIN, 20));
               jpHeaders.add(jlWelcome);
            jpTop.add(jpHeaders, BorderLayout.CENTER);
            
            //add button
            JPanel jpAdd = new JPanel(new BorderLayout());
               JButton jbAdd = new JButton("Add Publication");
               jbAdd.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent ae){
                     new AddEditWindow(false, FacultyView.this, user);
                  }
               });
               jpAdd.add(jbAdd, BorderLayout.WEST);
               
               //TO DO: add button for faculty inbox
            jpTop.add(jpAdd, BorderLayout.SOUTH);
         frame.add(jpTop, BorderLayout.NORTH);
         
         //publication table
         JPanel jpTable = new JPanel(new BorderLayout());
            jpTable.add(new JLabel("Title"), BorderLayout.NORTH);
            jpPapers = new JPanel(new GridLayout(0,1));
            JScrollPane jspPapers = new JScrollPane(jpPapers);
            jspPapers.setPreferredSize(new Dimension(TABLE_WIDTH,TABLE_HEIGHT));
            jpTable.add(jspPapers, BorderLayout.CENTER);
         frame.add(jpTable, BorderLayout.CENTER);
         
         //fill table
         reloadTable();
         
         //handle frame location and appearance
         frame.pack();
         frame.setLocationRelativeTo(null);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);
      }  //end else connected to database
   }  //end FacultyView constructor
   
   /**
    * Clears the publication table and then refills it by retrieving paper records from the db
    */
   public void reloadTable(){
      jpPapers.removeAll();
      ArrayList<Publication> papers = fManager.getPublications(user);
      for(Publication paper : papers){
         jpPapers.add(new FacultyPublicationRow(paper, this));
      }
      jpPapers.revalidate();
      jpPapers.repaint();
   }
   
   //TO DO: FacultyInbox inner class
   class FacultyInbox extends JFrame{

      public FacultyInbox(ArrayList<SpeakingRequest> messages){
         //window header
         
         //scrollpane for messages
         
         //for each speaking request
            
            //add a panel showing speaking request details
         
         //close button     
      }
   
   }
   
}  //end FacultyView class