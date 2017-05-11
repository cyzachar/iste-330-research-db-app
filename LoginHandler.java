import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

/* 
 *  Displays the Login interface for the faculty to login and view the FacultyView interface. 
 *   
 *  @author Team 17: Fareed Abolhassani, Abdulaziz Alshkrah, Craig Price, Cynthia Zachar
 */ 
public class LoginHandler extends JFrame implements ActionListener
{   
  /*
   *  main method that runs LoginHandler and calls SearchWindow
   */
     public static void main(String[] args)
   {
      LoginHandler lh = new LoginHandler(new SearchWindow());
   }
   //attributes
   private JPanel loginPanel = new JPanel();
   private JTextField jtfEmail;
   private JTextField jtfPassword;
   private SearchWindow mainWindow;
   
  /*
   *   Constructor contains parameter of SearchWindow object. Creates the GUI for the Faculty Login window 
   */
   public LoginHandler(SearchWindow _mainWindow)
   {
      mainWindow = _mainWindow;   
      setTitle("Login");
      setLocationRelativeTo(null);
      
     
      add(loginPanel, BorderLayout.CENTER);
      loginPanel.setLayout(new GridLayout(0,1));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // JLabel for Email
      JPanel jpEmail = new JPanel();
      JLabel jlEmail = new JLabel("Email:");
      jpEmail.add(jlEmail);
      loginPanel.add(jpEmail);
      
      // JTextfield for email
      jtfEmail = new JTextField();
      jtfEmail.setColumns(10);
      jpEmail.add(jtfEmail);
      
      // JLabel for Password
      JPanel jpPassword = new JPanel();
      JLabel jlPassword = new JLabel("Password:");
      jpPassword.add(jlPassword);
      loginPanel.add(jpPassword);  
      
      //JTextfield for Password
      jtfPassword = new JPasswordField(10);
      jtfPassword.setColumns(10);
      jpPassword.add(jtfPassword);
      
      // JPanel for button 
      JPanel buttonPane = new JPanel();
      add(buttonPane, BorderLayout.SOUTH);
      
      // Login Button that is given an actionListener to perform
      JButton loginButton = new JButton("Login");
      loginButton.setActionCommand("OK");
      buttonPane.add(loginButton);
      getRootPane().setDefaultButton(loginButton);
      loginButton.addActionListener(this);
         
      // Cancel button that closes the Login interface    
      JButton cancelButton = new JButton("Cancel");
      cancelButton.setActionCommand("Cancel");
      cancelButton.addActionListener(
               new ActionListener()
               {
                  public void actionPerformed(ActionEvent ae)
                  {
                     dispose();  
                  }
               });
      buttonPane.add(cancelButton);
         
      pack();
      // Allow the GUI to be visibile
      setVisible(true);
   } // end LoginHandler() contructor
   
  /*
   * actionPerformed method added to the Login button
   */
   public void actionPerformed(ActionEvent ae)
   {
      FacultyManager manager = new FacultyManager();
      
      // Checks if the login is valid or not      
      Faculty fac = manager.checkLogin(jtfPassword.getText(), jtfEmail.getText());
      if(fac == null)
      {
         JOptionPane.showMessageDialog(loginPanel,
            "Login Failed", "Failed to login", JOptionPane.WARNING_MESSAGE);      
      }
      else
      {
         // If login is valid open the FacultyView interface
         mainWindow.hide();
         FacultyView fv = new FacultyView(fac, mainWindow);
      }   
   }// end of actionPerformed()
}// end of LoginHandler