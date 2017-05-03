import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class LoginHandler extends JFrame implements ActionListener
{   
   public static void main(String[] args){
      LoginHandler lh = new LoginHandler();
   }
   JPanel loginPanel = new JPanel();
   JTextField jtfEmail;
   JTextField jtfPassword;

   public LoginHandler(){   
      setTitle("Login");
      setLocationRelativeTo(null);
      setSize(250,200);
      add(loginPanel, BorderLayout.CENTER);
      loginPanel.setLayout(new GridLayout(2,1));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      {
         JLabel jlEmail = new JLabel("Email:");
         loginPanel.add(jlEmail);
      }
      {
         jtfEmail = new JTextField();
         loginPanel.add(jtfEmail);
         jtfEmail.setColumns(10);
      }
      {
         JLabel jlPassword = new JLabel("Password:");
         loginPanel.add(jlPassword);  
      }
      {
         jtfPassword = new JPasswordField(10);
         loginPanel.add(jtfPassword);
         jtfPassword.setColumns(10);
      }
      {
         JPanel buttonPane = new JPanel();
         add(buttonPane, BorderLayout.SOUTH);
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
            cancelButton.addActionListener(
               new ActionListener(){
                  public void actionPerformed(ActionEvent ae){
                     dispose();  
                  }
               });
            buttonPane.add(cancelButton);
         }
      }
      setVisible(true);
   } // end LoginHandler contructor
   
   
   public void actionPerformed(ActionEvent ae)
   {
      FacultyManager manager = new FacultyManager();
      Faculty fac = new Faculty();
     
      fac = manager.checkLogin(fac.getPassword(), fac.getEmail());
      if(fac == null)
      {
         JOptionPane.showMessageDialog(loginPanel,
            "Login Failed","Failed to Login", JOptionPane.PLAIN_MESSAGE);       
      }
      else
      {
         FacultyView fv = new FacultyView(fac);
         
         //System.out.println("Login successful:\n" + fac);
      }   
   }
   
 /*  private void displayPublications(ArrayList<Publication> papers)
   {
      for(Publication paper : papers)
      {
         System.out.println(paper);
      }
   }
   
   private ArrayList<String> getParamArrayList(String... params)
   {
      ArrayList<String> paramList = new ArrayList<String>();
      for(String param : params)
      {
         paramList.add(param);
      }
      return paramList;
   }*/
}