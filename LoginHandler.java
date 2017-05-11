import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class LoginHandler extends JFrame implements ActionListener
{   
   public static void main(String[] args){
      LoginHandler lh = new LoginHandler(new SearchWindow());
   }
   private JPanel loginPanel = new JPanel();
   private JTextField jtfEmail;
   private JTextField jtfPassword;
   private SearchWindow mainWindow;

   public LoginHandler(SearchWindow _mainWindow){
      mainWindow = _mainWindow;   
      setTitle("Login");
      setLocationRelativeTo(null);
      //setSize(250,200);
      
      add(loginPanel, BorderLayout.CENTER);
      loginPanel.setLayout(new GridLayout(0,1));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
         JPanel jpEmail = new JPanel();
         JLabel jlEmail = new JLabel("Email:");
         jpEmail.add(jlEmail);
         loginPanel.add(jpEmail);
      
      
         jtfEmail = new JTextField();
         //loginPanel.add(jtfEmail);
         jtfEmail.setColumns(10);
         jpEmail.add(jtfEmail);
      
         JPanel jpPassword = new JPanel();
         JLabel jlPassword = new JLabel("Password:");
         jpPassword.add(jlPassword);
         loginPanel.add(jpPassword);  
      
      
         jtfPassword = new JPasswordField(10);
        // loginPanel.add(jtfPassword);
         jtfPassword.setColumns(10);
         jpPassword.add(jtfPassword);
      
      
         JPanel buttonPane = new JPanel();
         add(buttonPane, BorderLayout.SOUTH);
         
            JButton loginButton = new JButton("Login");
            loginButton.setActionCommand("OK");
            buttonPane.add(loginButton);
            getRootPane().setDefaultButton(loginButton);
            loginButton.addActionListener(this);
         
         
            JButton cancelButton = new JButton("Cancel");
            cancelButton.setActionCommand("Cancel");
            cancelButton.addActionListener(
               new ActionListener(){
                  public void actionPerformed(ActionEvent ae){
                     dispose();  
                  }
               });
            buttonPane.add(cancelButton);
         
      pack();
      setVisible(true);
   } // end LoginHandler contructor
   
   
   public void actionPerformed(ActionEvent ae)
   {
      FacultyManager manager = new FacultyManager();
     
      Faculty fac = manager.checkLogin(jtfPassword.getText(), jtfEmail.getText());
      if(fac == null)
      {
         JOptionPane.showMessageDialog(loginpanel,
         "Login Failed", "Failed to login", JOptionPane.WARNING_MESSAGE);    
      }
      else
      {
         mainWindow.hide();
         FacultyView fv = new FacultyView(fac, mainWindow);
      }   
   }
}