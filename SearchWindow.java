import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 * Displays the main user interface, which allows the user to search
 * (by title, faculty, or keyword) and view details for research publications.
 * 
 * Note that this class uses an AutoComplete jar file downloaded from: 
 * http://www.java2s.com/Code/Jar/s/Downloadswingxcore162AutoCompletitionjar.htm
 * This is a potentially outdated version but it's what was found.  Individual files appear to be available here:
 * https://java.net/projects/swingx/sources/svn/content/tags/SwingX-0-9-3/src/java/org/jdesktop/swingx/autocomplete/AutoCompleteDecorator.java
 * Documentation here: http://javadoc.geotoolkit.org/external/swingx/org/jdesktop/swingx/autocomplete/AutoCompleteDecorator.html
 *
 * @author Team 17: Fareed Abolhassani, Abdulaziz Alshkrah, Craig Price, Cynthia Zachar 
 */
public class SearchWindow{
   private JFrame frame;
   private JPanel jpResults;
   private JPanel jpSearchFields;
   private JPanel jpBottom;
   private JComboBox jcbOptions;
   private JTextField jtfSearchTerms;
   private PublicationManager pManager = new PublicationManager();
   
   private final String[] SEARCH_OPTIONS = {"Title","Faculty","Keyword"};
   private final int TABLE_HEIGHT = 200;
   private final int TABLE_WIDTH = 650;
   private final int TITLE_WIDTH = 400;
   private final int AUTHORS_WIDTH = 240;
   private final int TABLE_HEADER_HEIGHT = 20;
   
   //FOR TESTING ONLY
   public static void main(String[] args){
      new SearchWindow();
   }
   
   /**
    * Creates & displays the window that the user will interact with
    */
	public SearchWindow() {
		frame = new JFrame();
		frame.setTitle("RIT Research Database");

		//login button, header, & search box
		JPanel jpTop = new JPanel(new BorderLayout());
		//panel for login button & header
		JPanel jpLogin = new JPanel(new BorderLayout());
		//login button
		JButton jbLogin = new JButton("Faculty Login");
		jpLogin.add(jbLogin, BorderLayout.EAST);
		jbLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//open login window
            new LoginHandler(SearchWindow.this);
			}
		});

      //header
      JLabel jlHeader = new JLabel("RIT Research Database", JLabel.CENTER);
      jlHeader.setFont(new Font("headerFont", Font.PLAIN, 32));
      jpLogin.add(jlHeader, BorderLayout.SOUTH);
      jpTop.add(jpLogin, BorderLayout.NORTH);
         
      //search panel
		JPanel jpSearch = new JPanel(new BorderLayout());
		//search header
		JLabel jlSearchHeader = new JLabel("Search for a publication:");
		jpSearch.add(jlSearchHeader, BorderLayout.NORTH);

		//center panel for label, combobox, & textfield
		jpSearchFields = new JPanel();
		JLabel jlSearchBy = new JLabel("Search by");
		jpSearchFields.add(jlSearchBy);

		//search by options
		jcbOptions = new JComboBox(SEARCH_OPTIONS);
		jcbOptions.setEditable(false);
		jcbOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//clear text field
				jtfSearchTerms.setText("");

				//update decoration on text field
				undecorateTextField();
				ArrayList<String> autoCompleteOptions;
				switch (jcbOptions.getSelectedIndex()) {
				//faculty
				case 1:
					autoCompleteOptions = pManager.getAllFaculty();
					break;
				//keyword
				case 2:
					autoCompleteOptions = pManager.getAllKeywords();
					break;
				//title
				default:
					autoCompleteOptions = pManager.getAllTitles();
					break;
				}

				AutoCompleteDecorator.decorate(jtfSearchTerms, autoCompleteOptions, false);
			}
		});
		jpSearchFields.add(jcbOptions);

		//autocompleting text field
		jtfSearchTerms = new JTextField();
		jtfSearchTerms.setPreferredSize(new Dimension(300, 25));
		AutoCompleteDecorator.decorate(jtfSearchTerms, pManager.getAllTitles(), false);
		jpSearchFields.add(jtfSearchTerms);
		jpSearch.add(jpSearchFields, BorderLayout.CENTER);
            
		//search button
		JPanel jpSearchBtn = new JPanel(new BorderLayout());
		JButton jbSearch = new JButton("Search");
		jbSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				performSearch();
			}
		});
		jpSearchBtn.add(jbSearch, BorderLayout.WEST);
		jpSearch.add(jpSearchBtn, BorderLayout.SOUTH);
		jpTop.add(jpSearch, BorderLayout.CENTER);
		frame.add(jpTop, BorderLayout.NORTH);

		//results table
		JPanel jpTable = new JPanel(new BorderLayout());
		//table header
		JPanel jpTableHead = new JPanel();
		JLabel jlTitle = new JLabel("Title");
		jlTitle.setPreferredSize(new Dimension(TITLE_WIDTH, TABLE_HEADER_HEIGHT));
		jpTableHead.add(jlTitle, BorderLayout.NORTH);
            
      JLabel jlAuthors = new JLabel("Authors");
      jlAuthors.setPreferredSize(new Dimension(AUTHORS_WIDTH, TABLE_HEADER_HEIGHT));
      jpTableHead.add(jlAuthors, BorderLayout.NORTH);
      jpTable.add(jpTableHead, BorderLayout.NORTH);
         
         //panel for publication rows
      jpResults = new JPanel(new GridLayout(0,1));
      JScrollPane jspResults = new JScrollPane(jpResults);
      jspResults.setPreferredSize(new Dimension(TABLE_WIDTH,TABLE_HEIGHT));
      jpTable.add(jspResults, BorderLayout.CENTER);
      frame.add(jpTable, BorderLayout.CENTER);
      
      //initially fills table with all publications
      fillTable(pManager.fetchAllPublications());
      
      //bottom Jpannel
		jpBottom = new JPanel(new BorderLayout());
		JButton jbSubmit = new JButton("Submit speaking request");
		jpBottom.add(jbSubmit, BorderLayout.WEST);
		frame.add(jpBottom, BorderLayout.SOUTH);
		jbSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//SpeakingRequestView.spekingRequestAdd();
			}
		});
      //handles frame appearance & location
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }  //end SearchWindow constructor
   
   /**
    * Hides the search window
    */
   public void hide(){
      frame.setVisible(false);
   }
   
   /**
    * Shows the search window
    */
   public void show(){
      frame.setVisible(true);
   }
   
   /**
    * Fills the table of publications with the results of a search
    * @param papers   a list of Publications to add to the table
    */
   private void fillTable(ArrayList<Publication> papers){
      jpResults.removeAll();
      for(Publication paper : papers){
         jpResults.add(new PublicPublicationRow(paper));
      }
      jpResults.revalidate();
      jpResults.repaint();
   }
   
   /**
    * Performs the user-indicated search and updates the table
    */
   private void performSearch(){
      //check search type and fill table with results from that search
      String terms = jtfSearchTerms.getText();
      ArrayList<Publication> results = new ArrayList<Publication>();
      
      switch(jcbOptions.getSelectedIndex()){
         //faculty
         case 1:  results = pManager.fetchPublicationsByFaculty(terms);
                  break;
         //keyword
         case 2:  results = pManager.fetchPublicationsByKeyword(terms);
                  break;
         //title
         default: results = pManager.fetchPublicationsByTitle(terms);   
                  break;
      }
      
      //no results => show msg, clear table
      if(results.size() < 1){
         JOptionPane.showMessageDialog(null, "No results found", "No results", JOptionPane.INFORMATION_MESSAGE);
         jpResults.removeAll();
         jpResults.revalidate();
         jpResults.repaint();
      }
      //results => fill table
      else{
         fillTable(results);
      }
   }
   
   /**
    * Prevents the search term text field from autocompleting with terms
    * outside the selected category (titles, faculty, or keywords) by
    * completely removing and replacing the old text field.
    */
   private void undecorateTextField(){
      jpSearchFields.remove(jtfSearchTerms);
      jtfSearchTerms = new JTextField();
      jtfSearchTerms.setPreferredSize(new Dimension(300,25));
      jpSearchFields.add(jtfSearchTerms);
      jpSearchFields.revalidate();
      jpSearchFields.repaint();
   }

}  //end SearchWindow class
