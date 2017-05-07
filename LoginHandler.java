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
     
      Faculty fac = manager.checkLogin(jtfPassword.getText(), jtfEmail.getText());
      if(fac == null)
      {
         JOptionPane.showMessageDialog(loginPanel,
            "Login Failed","Failed to Login", JOptionPane.PLAIN_MESSAGE);       
      }
      else
      {
         mainWindow.hide();
         FacultyView fv = new FacultyView(fac, mainWindow);
         
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