import java.awt.*;
import javax.swing.*;

public class MessageRow extends JPanel {
	
	private SpeakingRequest speakingRequest;
	private final int FONT_SIZE = 14;

	// main is only for testing purposes
	public static void main(String[] args) {
		JFrame jfMain = new JFrame();
		jfMain.add(new MessageRow(new SpeakingRequest("P.H.Pereira", "phpereira@gmail.com", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eu interdum orci. Donec sed sagittis sem. Aliquam mauris nulla, pellentesque ut enim vel, sollicitudin suscipit eros. Etiam dictum congue arcu, eget bibendum tellus convallis in. Phasellus tempus nulla vitae dolor posuere vestibulum. Sed ut consectetur urna. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed condimentum erat justo, at auctor ex sollicitudin ut. Phasellus nec dignissim mauris. ")),
				BorderLayout.CENTER);
		jfMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfMain.setLocationRelativeTo(null);
		jfMain.pack();
		jfMain.setVisible(true);
	}

	public MessageRow(SpeakingRequest _speakingRequest) {
		speakingRequest = _speakingRequest;
		Font rowFont = new Font("rowFont", Font.PLAIN, FONT_SIZE);
      setLayout(new BorderLayout());
      
      JPanel jpDetails = new JPanel(new FlowLayout(FlowLayout.LEFT));
   		JPanel namePanel = new JPanel(new BorderLayout());
      		JLabel nameLbl = new JLabel("Name :");
      		nameLbl.setFont(rowFont);
      		JLabel nameTxt = new JLabel(speakingRequest.getRequesterName());
      		nameTxt.setFont(rowFont);
      		namePanel.add(nameLbl, BorderLayout.LINE_START);
      		namePanel.add(nameTxt, BorderLayout.LINE_END);
   		jpDetails.add(namePanel);
   
   		JPanel emailPanel = new JPanel(new BorderLayout());
      		JLabel emailLbl = new JLabel("Email :");
      		emailLbl.setFont(rowFont);
      		JLabel emailTxt = new JLabel(speakingRequest.getRequesterEmail());
      		emailTxt.setFont(rowFont);
      		emailPanel.add(emailLbl, BorderLayout.LINE_START);
      		emailPanel.add(emailTxt, BorderLayout.LINE_END);
   		jpDetails.add(emailPanel);
         
         if(speakingRequest.getRequesterPhone().length() > 0){
      		JPanel phonePanel = new JPanel(new BorderLayout());
         		JLabel phoneLbl = new JLabel("Phone :");
         		phoneLbl.setFont(rowFont);
         		JLabel phoneTxt = new JLabel(speakingRequest.getRequesterPhone());
         		phoneTxt.setFont(rowFont);
         		phonePanel.add(phoneLbl, BorderLayout.LINE_START);
         		phonePanel.add(phoneTxt, BorderLayout.LINE_END);
      		jpDetails.add(phonePanel);
         }
      add(jpDetails, BorderLayout.NORTH);

		JPanel messagePanel = new JPanel(new BorderLayout());
   		JLabel messageLbl = new JLabel("Message :");
   		messageLbl.setFont(rowFont);
         JTextArea jtaMsg = new JTextArea(speakingRequest.getMsg());
         jtaMsg.setLineWrap(true);
         jtaMsg.setWrapStyleWord(true);
         jtaMsg.setEditable(false);
         jtaMsg.setBackground(null);
   		jtaMsg.setFont(rowFont);
   		messagePanel.add(messageLbl, BorderLayout.NORTH);
         messagePanel.add(jtaMsg, BorderLayout.CENTER);
		add(messagePanel, BorderLayout.CENTER);

	} // end MessageRow constructor
   
}  //end MessageRow class
