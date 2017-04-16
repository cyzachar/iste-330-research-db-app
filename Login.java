/**

Login Class - a class that provides a screen to login to the database application and autheticate the user 

@author Fareed Abolhassani, 
@version 0.1
**/


import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Login extends JDialog implements ActionListener{

//Variables 
private final JPanel contentPanel = new JPanel();
private JTextField textUsername;
private JTextField textPassword;
private Database db = new Database();
private SearchWindow as = null;
private String role_1="";

//dialog 
public Login(SearchWindow asIn){
   as = asIn;
   setTitle("Login Window");
   setLocationRelativeTo(null);
   setBounds(100,100,399,145);
   getContentPane().setLayout(new BorderLayout());
   contentPanel.setBorder(new EmptyBorder(5,5,5,5));
   getContentPane().add(contentPanel, BorderLayout.CENTER);
   contentPanel.setLayout(new GridLayout(2,2));
   {
      JLabel lblUsername = new JLabel("Username:");
      contentPanel.add(lblUsername);
   }
   {
      textUsername = new JTextField();
      contentPanel.add(textUsername);
      textUsername.setColumns(10);
   }
   {
      JLabel lblPassword = new JLabel("Password:");
      contentPanel.add(lblPassword);  
   }
   {
      textPassword = new JPasswordField(10);
      contentPanel.add(textPassword);
      textPassword.setColumns(10);
   }
   {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {
         JButton loginButton = new JButton("Login");
         loginButton.setActionCommand("OK");
         buttonPane.add(loginButton);
         getRootPane().setDefaultButton(loginButton);
         loginButton.addActionListener(this);
      }
      {
         JButton cancelButton = new JButton("Cancel");
         cancelButton.setActionCommand("Cancel");
         cancelButton.addActionListener(this);
         buttonPane.add(cancelButton);
      }
   }
   
}

public Login(){
   //constructor stub
}


/* This method is used for authenticating users and check role
*
*
*/
public void actionPerformed(ActionEvent ae){
   if(ae.getActionCommand() == ("OK")){
      db.connect();
      boolean connected = db.authenticateUser(textUsername.getText().trim(), textPassword.getText().trim());
      if(!connected){
         JOptionPane.showMessageDialog(this,
            "Invalid username or password",
            "Login Failure",
            JOptionPane.ERROR_MESSAGE);
      }
      else{
         String role = db.getRole(textUsername.getText());
         if(role.equalsIgnoreCase("faculty")){
            role_1 = "Faculty";
            this.dispose();
            as.isFaculty();
         }
         else if(role.equalsIgnoreCase("admin")){
            role_1 = "Admin";
            this.dispose();
            as.isAdmin();
         }
         else if(role.equalsIgnoreCase("student")){
            role_1 = "Student";
            this.dispose();
            as.isStudent();
         }
         else{
            this.dispose();
         }
        }
     textUsername.setText("");
     textPassword.setText("");
  }

      else if(ae.getActionCommand() =="Cancel"){
         dispose();
      }
   }
   
   public String userName(){
   //check role 
	return textUsername.getText().trim() + ", you are a " + role_1;    }
}