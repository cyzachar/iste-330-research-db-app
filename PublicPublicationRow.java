import java.util.*;        //for ArrayList
import javax.swing.*;      //for JPanel
import java.awt.*;         //for Cursor
import java.awt.event.*;   //for MouseAdapter

/**
 * Displays paper title and authors and shows details when clicked, also
 * allowing the user to contact the paper's authors
 * @author Team 17: Fareed Abolhassani, Abdulaziz Alshkrah, Craig Price, Cynthia Zachar
 */
public class PublicPublicationRow extends JPanel{
   private Publication paper;
   private String authors = "";
   private final int TITLE_WIDTH = 400;
   private final int AUTHORS_WIDTH = 200;
   private final int LABEL_HEIGHT = 20;
   private final int FONT_SIZE = 14;
   private final int DETAILS_HEIGHT = 300;
   private final int DETAILS_WIDTH = 500;
   
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
      jlAuthors.setToolTipText(authors);
      addRowMouseListener(jlAuthors);
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
         setPreferredSize(new Dimension(DETAILS_WIDTH,DETAILS_HEIGHT));
         
         JPanel jpTopInfo = new JPanel(new GridLayout(0,1));
            //title
            String title = paper.getTitle();
            JLabel jlTitle = new JLabel(title, SwingConstants.CENTER);
               Font titleFont = new Font("titleFont", Font.PLAIN, 30);
               jlTitle.setFont(titleFont);
               jlTitle.setToolTipText(title);
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
         JPanel jpLowerInfo = new JPanel(new BorderLayout());
            jpLowerInfo.add(getTextBlock("Abstract: " + paper.getAbstract(),true),BorderLayout.CENTER);
            jpLowerInfo.add(getTextBlock("Citation: " + paper.getCitation(),false),BorderLayout.SOUTH);
         add(jpLowerInfo, BorderLayout.CENTER);
         
         //contact button
         JPanel jpContact = new JPanel(new BorderLayout());
            JButton jbContact = new JButton("Contact author" + ((authors.contains(",")) ? "s" : ""));
            jbContact.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent ae){
                  new ContactForm();
               }
            });
            jpContact.add(jbContact, BorderLayout.EAST);
         add(jpContact, BorderLayout.SOUTH);
         
         //window appearance and location
         pack();
         setLocationRelativeTo(null);
         setDefaultCloseOperation(DISPOSE_ON_CLOSE);
         setVisible(true);
         
      }  //end PublicationDetails constructor

    }  //end PublicationDetails class
    
    /**
     * Provides a window that the user can use to contact a faculty member
     */
    class ContactForm extends JFrame{
      private SpeakingRequestManager srManager = new SpeakingRequestManager();
      private ArrayList<JCheckBox> authorCheckBoxes = new ArrayList();
      
      /**
       * Displays a contact window with fields for name, email, phone, and message 
       * and checkboxes for recipients
       */
      public ContactForm(){
         setTitle("Faculty Contact Form");
         setPreferredSize(new Dimension(400,400));
         
         //header & description
         JPanel jpTop = new JPanel(new BorderLayout());
            JLabel jlHeader = new JLabel("Contact Form");
            jpTop.add(jlHeader,BorderLayout.NORTH);
            jpTop.add(getTextBlock("Use this form to send this paper's author(s) comments or questions.  "
                                 + "If you'd like to ask faculty to speak at an event, "
                                 + "please include date, time, and location details with your message.",false),BorderLayout.SOUTH);
         add(jpTop, BorderLayout.NORTH);
         
         //all fields - from name to msg
         JPanel jpCenter = new JPanel(new BorderLayout());
            //all textfields and checkboxes with their labels
            JPanel jpFields = new JPanel(new BorderLayout());
               //all textfields
               JPanel jpTextFields = new JPanel(new GridLayout(0,1));
                  //name
                  JPanel jpName = new JPanel();
                     jpName.add(new JLabel("Your name*: "));
                     JTextField jtfName = new JTextField(20);
                     jpName.add(jtfName);
                  jpTextFields.add(jpName);
                  
                  //email
                  JPanel jpEmail = new JPanel();
                     jpEmail.add(new JLabel("Your email*: "));
                     JTextField jtfEmail = new JTextField(20);
                     jpEmail.add(jtfEmail);
                  jpTextFields.add(jpEmail);
                  
                  //phone
                  JPanel jpPhone = new JPanel();
                     jpPhone.add(new JLabel("Your phone#: "));
                     JTextField jtfPhone = new JTextField(20);
                     jpPhone.add(jtfPhone);
                  jpTextFields.add(jpPhone);
               jpFields.add(jpTextFields, BorderLayout.NORTH);
               
               //author checkboxes
               JPanel jpContacts = new JPanel(new BorderLayout());
                  jpContacts.add(new JLabel("Faculty to contact*: "), BorderLayout.NORTH);
                  JPanel jpAuthors = new JPanel(new GridLayout(0,1));
                     for(String author : paper.getAuthors()){
                        JCheckBox jcbAuthor = new JCheckBox(author);
                        authorCheckBoxes.add(jcbAuthor);
                        jpAuthors.add(jcbAuthor);
                     }
                  jpContacts.add(jpAuthors, BorderLayout.CENTER);
               jpFields.add(jpContacts, BorderLayout.SOUTH);
            jpCenter.add(jpFields, BorderLayout.NORTH);
            
            //msg text area   
            JPanel jpMsg = new JPanel(new BorderLayout());
               jpMsg.add(new JLabel("Message*:"), BorderLayout.NORTH);
               JTextArea jtaMsg = new JTextArea();
               jtaMsg.setLineWrap(true);
               jtaMsg.setWrapStyleWord(true);
               jpMsg.add(jtaMsg, BorderLayout.CENTER);
            jpCenter.add(jpMsg, BorderLayout.CENTER);
         add(jpCenter, BorderLayout.CENTER);
         
         //send button
         JPanel jpControls = new JPanel(new BorderLayout());
            JButton jbSend = new JButton("Send");
            
            //send action
            jbSend.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent ae){
                  String missingInfo = "";
                  
                  //get text from fields
                  String name = jtfName.getText();
                  String email = jtfEmail.getText();
                  String msg = jtaMsg.getText();
                  
                  //validate name
                  if(name.length() == 0){
                     missingInfo += "name, ";
                  }
                  //validate email
                  if(email.length() == 0){
                     missingInfo += "email, ";
                  }
                  
                  //gather recipient names from checkboxes
                  ArrayList<String> recipients = new ArrayList<String>();
                  for(JCheckBox author : authorCheckBoxes){
                     if(author.isSelected()){
                        recipients.add(author.getText());
                     }
                  }
                  
                  //validate recipients
                  if(recipients.size() == 0){
                     missingInfo += "recipient(s), ";
                  }
                  
                  //validate msg
                  if(msg.length() == 0){
                     missingInfo += "message, ";
                  }
                  
                  //some info missing => error msg
                  if(missingInfo.length() > 0){
                     missingInfo = missingInfo.substring(0, missingInfo.length() - 2);
                     JOptionPane.showMessageDialog(null,"Please provide " + missingInfo, "Required Information Omitted", JOptionPane.WARNING_MESSAGE);
                  }
                  //all info provided => continue
                  else{
                     String phone = jtfPhone.getText();
                     //phone provided
                     if(phone.length() > 0){
                        phone = phone.replaceAll("[ ()-]","");
                        System.out.println(phone + ": " + phone.length() + ";" + (new Scanner(phone)).hasNextInt());
                        //invalid phone
                        if(!phone.matches("^[0-9]{10}$")){
                           JOptionPane.showMessageDialog(null,"Provided phone number is invalid", "Invalid Phone Number", JOptionPane.WARNING_MESSAGE);
                        }
                        //valid phone
                        else{
                           //send successful
                           if(srManager.addRequest(name,email,phone,recipients,msg)){
                              JOptionPane.showMessageDialog(null,"Your message was sent successfully","Submission Successful",JOptionPane.INFORMATION_MESSAGE);
                              ContactForm.this.dispose();
                           }
                           //send unsuccessful
                           else{
                              JOptionPane.showMessageDialog(null,"Your message could not be sent","Submission Unsuccessful",JOptionPane.ERROR_MESSAGE);
                           }
                        }
                     }
                     //phone not provided
                     else{
                        //send successful
                        if(srManager.addRequest(name,email,recipients,msg)){
                           JOptionPane.showMessageDialog(null,"Your message was sent successfully","Submission Successful",JOptionPane.INFORMATION_MESSAGE);
                           ContactForm.this.dispose();
                        }
                        //send unsuccessful
                        else{
                           JOptionPane.showMessageDialog(null,"Your message could not be sent","Submission Unsuccessful",JOptionPane.ERROR_MESSAGE);
                        }
                     }  //end phone omitted
                  }  //end all info provided
               }  //end actionPerformed
            });
            jpControls.add(jbSend, BorderLayout.EAST);
         add(jpControls, BorderLayout.SOUTH);
      
         //window appearance and location
         pack();
         setLocationRelativeTo(null);
         setDefaultCloseOperation(DISPOSE_ON_CLOSE);
         setVisible(true);
      }
      
    } //end ContactForm
    
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