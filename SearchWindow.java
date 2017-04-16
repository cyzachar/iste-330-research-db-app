import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;


public class SearchWindow extends JFrame implements MenuListener, ActionListener, TableModelListener{

   public int selectedID;
   public ArrayList<String> selectedList = new ArrayList<String>();
   private JPanel contentPane;
   private Database db = new Database();
   
   //menubar 
   private JMenuBar menuBar = new JMenuBar();
   private JMenu menuFile = new JMenu("File");
   private JMenu menuLogout= new JMenu("Logout");
   private JMenu menuReset = new JMenu("Reset");
   private JMenu menuExit = new JMenu("Exit");
   private JMenu menuHelp = new JMenu("Help");
   private JMenu menuAbout = new JMenu("About");
   private JMenu menuHowto = new JMenu("How to use");
   Login login;
   
   //search variables
   private JTextField textKeyword;
   private JTextField textTitle;
   private JTextField textAuthor;
   private JLabel labelKeyWord = new JLabel("Keyword");
   private JLabel labelAuthor = new JLabel("Author");
   private JLabel labelTitle = new JLabel("Title");
   private JLabel labelMessage= new JLabel();   
   
   //title panel 
   private JPanel jPanelTitle = new JPanel();
   private JLabel labelWelcome = new JLabel("Welcome to Database");
   private JLabel labelHello = new JLabel("Hello!");
   
   //result area
   private JPanel panelSearchArea = new JPanel();
   private JPanel panelbottomButton = new JPanel();
   private JTable tableResult = new JTable();
   private DefaultTableModel model = new DefaultTableModel();
   private JButton buttonSearch = new JButton("Search");
   private JButton buttonClear = new JButton("Clear");
   private JButton buttonLogout = new JButton("Logout");
   private JButton buttonAdd = new JButton("Add");
   private JButton buttonUpdate = new JButton("Update");
   private JButton buttonDelete = new JButton("Delete");
   private JButton buttonAdduser= new JButton("Add User");
   private JButton buttonLogin = new JButton("Login");

   //frame
   public SearchWindow(){
      setTitle("Search Window");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100,100,600,350);
      setSize(1000,500);
      
      //set menubar content
      setJMenuBar(menuBar);
      menuBar.add(menuFile);
      menuBar.add(menuLogout);
      menuLogout.addActionListener(this);
      menuBar.add(menuReset); 
      menuReset.addActionListener(this);
      menuBar.add(menuExit);
      menuExit.addActionListener(this);
      menuBar.add(menuHelp);
      menuHelp.addActionListener(this);
      menuBar.add(menuAbout);
      menuAbout.addActionListener(this);
      menuBar.add(menuHowto);
      menuHowto.addActionListener(this);
      
      //main panel
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5,5,5,5));
      contentPane.setLayout(new BorderLayout(0,0));
      setContentPane(contentPane);
      
      //north
      contentPane.add(jPanelTitle, BorderLayout.NORTH);
      jPanelTitle.setLayout(new GridLayout(2,1));
      labelWelcome.setHorizontalAlignment(SwingConstants.CENTER);
      labelWelcome.setFont(new Font("Tahoma", Font.BOLD, 19));
      labelWelcome.setForeground(Color.green);
      jPanelTitle.add(labelWelcome);
      labelHello.setHorizontalAlignment(SwingConstants.TRAILING);
      jPanelTitle.add(labelHello);
      
      //west
      contentPane.add(panelSearchArea, BorderLayout.WEST);
      panelSearchArea.setLayout(new GridLayout(4, 2));
      
      panelSearchArea.add(labelKeyWord);
      textKeyword = new JTextField();
      panelSearchArea.add(textKeyword);
      textKeyword.setColumns(10);
      
      panelSearchArea.add(labelAuthor);
      textAuthor = new JTextField();
      panelSearchArea.add(textAuthor);
      textAuthor.setColumns(10);
      labelMessage.setForeground(Color.RED);
      panelSearchArea.add(labelMessage);
      
      //bottom 
      contentPane.add(panelbottomButton, BorderLayout.SOUTH);
      
      buttonSearch.addActionListener(this);
		panelbottomButton.add(buttonSearch);
		buttonClear.addActionListener(this);
		panelbottomButton.add(buttonClear);
		buttonAdd.addActionListener(this);
		panelbottomButton.add(buttonAdd);
		buttonUpdate.addActionListener(this);
		panelbottomButton.add(buttonUpdate);
		buttonDelete.addActionListener(this);
		panelbottomButton.add(buttonDelete);
		buttonLogout.addActionListener(this);
		panelbottomButton.add(buttonLogout);
		buttonAdduser.addActionListener(this);
		panelbottomButton.add(buttonAdduser);
		panelbottomButton.add(buttonLogin);
		buttonLogin.addActionListener(this);
		panelbottomButton.add(buttonLogin);
      isPublic();
      
     	model = new DefaultTableModel(null, new String[] { "Select", "ID", "Title", "Abstract", "Citation" }) {
      
      
      public Class getColumnClass(int c){
         switch(c){
         case 0:
            return Boolean.class;
         
         case 1:
            return String.class;
         
         case 2:
            return String.class;
         
         case 3:
            return String.class;
         
         default:
            return String.class;
         
         }
      }
    };
    
    tableResult = new JTable(model);
    tableResult.getColumn("ID").setPreferredWidth(0);
    tableResult.getColumn("ID").setMinWidth(0);
    tableResult.getColumn("ID").setMaxWidth(0);
    TableColumnModel colModel = tableResult.getColumnModel();
    //data in string
    colModel.getColumn(0).setPreferredWidth(5);
    tableResult.setRowHeight(40);
    JScrollPane scrollPane = new JScrollPane(tableResult);
    tableResult.setFillsViewportHeight(true);
    tableResult.getTableHeader().setReorderingAllowed(false);
    contentPane.add(scrollPane, BorderLayout.CENTER);
    tableResult.getModel().addTableModelListener(this);
   }
   
   //action 
   public void actionPerformed(ActionEvent ae){
      if(ae.getActionCommand() == ("Reset") || ae.getActionCommand() == "Clear"){
         textKeyword.setText("");
         textTitle.setText("");
         textAuthor.setText("");
         clearTable();
      }
      else if(ae.getActionCommand() == "Search"){
      clearTable();
      String authorName = textAuthor.getText().trim();
      String title = textTitle.getText().trim();
      String keyWords = textKeyword.getText().trim();
      
      db.connect();
      ArrayList<String> column = new ArrayList<String>();
      ArrayList<String> values = new ArrayList<String>();
      
      if(!authorName.isEmpty()){
         column.add("person.fname");
         values.add(textAuthor.getText().trim());   
      }
      if(!title.isEmpty()){
         column.add("title");
         values.add(textTitle.getText().trim());
      }
      if(!keyWords.isEmpty()){
         column.add("keyword");
         values.add(textKeyword.getText().trim());
      }
      if(authorName.isEmpty() && keyWords.isEmpty() && title.isEmpty()){
         column.add("person.fname");
         values.add(textAuthor.getText().trim());
      }
      
      db.bigList.clear();
      db.bigList.clear();
      
      db.fetch(column,values);
      if(db.bigList.size() > 0 ){
         for(int i = 0; i < db.bigList.size();i++){
            ArrayList<String> small = db.bigList.get(i);
            model.addRow(new Object[] { false, small.get(0), small.get(1), small.get(2), small.get(3) });         
         }
      }else {
      labelMessage.setText("No Record found");
      }
   } 
   else if (ae.getActionCommand() == "Login"){
      login = new Login(this);
      this.setVisible(true);
      login.setVisible(true);
    }
    else if(ae.getActionCommand().equalsIgnoreCase("Exit")) {
      System.exit(0);
   }
   else if (ae.getActionCommand().equalsIgnoreCase("How to use")){
      openPage();
   }
   else if (ae.getActionCommand().equalsIgnoreCase("About")) {

			JOptionPane.showMessageDialog(null, "Search Database " + "By Group 17\n " + "\2017 \n"
					+ "Fareed Abolhassani\n" + "<Name>\n" + " <Name> \n" + "<Name>\n");
		}
     else if (ae.getActionCommand().equals("Delete")) {
			if(JOptionPane.showConfirmDialog(null,"Are you sure you want to delete " + selectedList.get(1) ) == 0){
				if (db.deletePapers(selectedID)) {
						JOptionPane.showMessageDialog(null, "Deleted paper "+ selectedList.get(1));
						clearTable();
				}
			}
		} 
     else if (ae.getActionCommand().equals("Add")) {
			AddWindow ad = new AddWindow();
			setVisible(true);
			ad.setVisible(true);

		}
		else if (ae.getActionCommand().equals("Logout")) {
			isPublic();

		}
		else if (ae.getActionCommand().equalsIgnoreCase("Add user")) {
			AddUser aduser = new AddUser();
			this.setVisible(true);
			aduser.setVisible(true);
		} 
      else if (ae.getActionCommand().equalsIgnoreCase("Update")) {
			db.smallList.clear();
			db.bigList.clear();
			EditWindow edit = new EditWindow();
			edit.textId.setText(selectedList.get(0));
			edit.textTitle.setText(selectedList.get(1));
			edit.textcitation.setText(selectedList.get(3));
			edit.textAbstract.setText(selectedList.get(2));
			edit.textAbstract.setWrapStyleWord(true);
			edit.textAbstract.setLineWrap(true);
			setVisible(true);
			edit.setVisible(true);
		}
}

   //event listener for table
   public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel) e.getSource();
		if (column > -1) {
			Object data = model.getValueAt(row, column);
			selectedList.clear();
			if ((boolean) data == true) {
				selectedID = Integer.parseInt(model.getValueAt(row, column + 1).toString());
				selectedList.add(model.getValueAt(row, column + 1).toString());
				selectedList.add(model.getValueAt(row, column + 2).toString());
				selectedList.add(model.getValueAt(row, column + 3).toString());
				selectedList.add(model.getValueAt(row, column + 4).toString());
				for (int i = 0; i < selectedList.size(); i++) {
						//test loop do not do anything
				}
			} else {
				selectedID = -1;
			}
		}
	}
   
 //clear table
	private void clearTable() {
		int rows = model.getRowCount();
		labelMessage.setText("");
		for (int i = rows - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}

 //visibility 
	public void isAdmin() {
		buttonUpdate.setEnabled(true);
		buttonDelete.setEnabled(true);
		buttonLogout.setEnabled(true);
		buttonAdduser.setEnabled(true);
		buttonAdd.setEnabled(true);
		buttonLogin.setVisible(false);
		labelHello.setText("Hello " +login.userName());
		labelHello.setForeground(Color.red);
	}

	public void isFaculty() {
		buttonUpdate.setEnabled(true);
		buttonDelete.setEnabled(true);
		buttonLogout.setEnabled(true);
		buttonAdduser.setEnabled(false);
		buttonAdd.setEnabled(true);
		labelHello.setText("Hello " +login.userName());
		labelHello.setForeground(Color.red);
	}
	private void isPublic() {
		buttonUpdate.setEnabled(false);
		buttonDelete.setEnabled(false);
		buttonLogout.setEnabled(false);
		buttonAdduser.setEnabled(false);
		buttonAdd.setEnabled(false);
		buttonLogin.setEnabled(true);
		buttonLogin.setVisible(true);
		labelHello.setText("Hello");
		labelHello.setForeground(Color.red);
	}

	public void isStudent() {
		buttonUpdate.setEnabled(false);
		buttonDelete.setEnabled(false);
		buttonLogout.setEnabled(false);
		buttonAdduser.setEnabled(false);
		buttonAdd.setEnabled(false);
		buttonLogin.setEnabled(true);
		buttonLogin.setVisible(true);
		labelHello.setText("Hello " +login.userName());
		labelHello.setForeground(Color.red);
	}

   // opens help website locally via help menu with default web browser
	public void openPage(){
		String htmlFilePath = "help/index.html";
		File htmlFile = new File(htmlFilePath);
		try {
			Desktop.getDesktop().open(htmlFile);
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {	
			e.printStackTrace();
		}

	}
	@Override
	public void menuCanceled(MenuEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void menuDeselected(MenuEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void menuSelected(MenuEvent arg0) {
		// TODO Auto-generated method stub

}
   
}