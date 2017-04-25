import javax.swing.*;
import java.awt.*;

public class PublicationDetails extends JFrame{
   
   public PublicationDetails(String title, String authors, String paperAbstract, String citation){
      setTitle(title);
      setLayout(new GridLayout(0,1));
      setMinimumSize(new Dimension(500,0));
      
      JLabel jlTitle = new JLabel(title, SwingConstants.CENTER);
         Font titleFont = new Font("titleFont", Font.PLAIN, 30);
         jlTitle.setFont(titleFont);
      add(jlTitle);
      
      JLabel jlAuthors = new JLabel(authors, SwingConstants.CENTER);
         Font authorsFont = new Font("authorsFont", Font.PLAIN, 25);
         jlTitle.setFont(authorsFont);
      add(jlAuthors);
      
      add(new JLabel("  Abstract: " + paperAbstract));
      add(new JLabel("  Citation: " + citation));
      
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      setLocationRelativeTo(null);
      pack();
      setVisible(true);
   }

}