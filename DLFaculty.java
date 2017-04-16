import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DLFaculty {

	private int id;
	private String firstName;
	private String lastName;
	private String password;
	private String email;

	public DLFaculty() 
   {
		super();
  	}

	public DLFaculty(int id, String firstName, String lastName, String password, String email) 
   {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
	}

	public int getId() 
   {
		return id;
	}

	public void setId(int id) 
   {
		this.id = id;
	}

	public String getFirstName() 
   {
		return firstName;
	}

	public void setFirstName(String firstName) 
   {
		this.firstName = firstName;
	}

	public String getLastName() 
   {
		return lastName;
	}

	public void setLastName(String lastName) 
   {
		this.lastName = lastName;
	}

	public String getPassword() 
   {
		return password;
	}

	public void setPassword(String password) 
   {
		this.password = password;
	}

	public String getEmail() 
   {
		return email;
	}

	public void setEmail(String email) 
   {
		this.email = email;
	}

	//method to insert faculty data when creating a new faculty
	public boolean insertFaculty(ResearchDb con) throws DLException 
   {

		String query = " insert into faculty (id,fName, lName, password, email)" + " values (?,?, ?, ?, ?)";
		try {
			ArrayList<String> dataList = new ArrayList<String>();
			dataList.add(Integer.toString(this.getId()));
			dataList.add(this.getFirstName());
			dataList.add(this.getLastName());
			dataList.add(this.getPassword());
			dataList.add(this.getEmail());

			System.out.println("Connection: " + con);
			return con.setData(query, dataList);
		} catch (Exception ex) {
			Logger.getLogger(DLPublication.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	//method to get all the faculty data when the faculty member name is given.
	public DLFaculty getFacultyData(ResearchDb con) throws DLException 
   {
		String query = "Select * from faculty where fName = ? and lName = ?";

		try {
			DLFaculty faculty = new DLFaculty();
			ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
			ArrayList<String> dataList = new ArrayList<String>();
			dataList.add(this.getFirstName());
			dataList.add(this.getLastName());

			result = con.getData(query, dataList, false);
			if (result == null)
				System.out.println("Null");
			else {
				faculty.setId(Integer.parseInt(result.get(0).get(0)));
				faculty.setFirstName(result.get(0).get(1));
				faculty.setLastName(result.get(0).get(2));
				faculty.setPassword(result.get(0).get(3));
				faculty.setEmail(result.get(0).get(4));
			}
			return faculty;
		} catch (Exception ex) {
			Logger.getLogger(DLPublication.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	//get faculty data when faculty id is provided
	public DLFaculty getFacultyById(ResearchDb con) throws DLException 
   {
		String query = "Select * from faculty where id = ?";

		try {
			DLFaculty faculty = new DLFaculty();
			ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
			ArrayList<String> dataList = new ArrayList<String>();
			dataList.add(Integer.toString(this.getId()));

			result = con.getData(query, dataList, false);

			faculty.setId(Integer.parseInt(result.get(0).get(0)));
			faculty.setFirstName(result.get(0).get(1));
			faculty.setLastName(result.get(0).get(2));
			faculty.setPassword(result.get(0).get(3));
			faculty.setEmail(result.get(0).get(4));

			return faculty;
		} catch (Exception ex) {
			Logger.getLogger(DLPublication.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}

	}

	//Verify faculty login
	public boolean verifyFaculty(ResearchDb con) throws DLException 
   {
		String query = "select * from faculty where password = ? and email = ?";

		try {
			ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
			ArrayList<String> dataList = new ArrayList<String>();
			dataList.add(this.getPassword());
			dataList.add(this.getEmail());

			result = con.getData(query, dataList, false);

			if (result.size()!=0) {
				this.setId(Integer.parseInt(result.get(0).get(0)));
				this.setFirstName(result.get(0).get(1));
				this.setLastName(result.get(0).get(2));
				this.setPassword(result.get(0).get(3));
				this.setEmail(result.get(0).get(4));
				return true;
			} else
				return false;
		} catch (Exception ex) {
			Logger.getLogger(DLPublication.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	//Update faculty data.
	public boolean updateFaculty(ResearchDb con) throws DLException 
   {
		String query = "update faculty set fName = ?, lName = ?, password = ?, email = ? where id = ?";

		ArrayList<String> dataList = new ArrayList<String>();
		try {
			dataList.add(this.getFirstName());
			dataList.add(this.getLastName());
			dataList.add(this.getPassword());
			dataList.add(this.getEmail());
			dataList.add(Integer.toString(this.getId()));

			return con.setData(query, dataList);

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	//delete a faculty
	public boolean deleteFaculty(ResearchDb con) throws DLException 
   {
		String query = "delete from faculty where id = ?";

		ArrayList<String> dataList = new ArrayList<String>();

		try {
			dataList.add(Integer.toString(this.getId()));

			return con.setData(query, dataList);

		} catch (Exception ex) {
			Logger.getLogger(DLPublication.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	//get all the publication ids' when a faculty id is given
	public ArrayList<Integer> getPublications(ResearchDb con) throws DLException 
   {
		String query = "select paperId from authorship where facultyId = ?";

		try {

			ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
			ArrayList<String> dataList = new ArrayList<String>();
			ArrayList<Integer> publications = new ArrayList<Integer>();
			dataList.add(Integer.toString(this.getId()));

			result = con.getData(query, dataList, false);
			for (int i = 0; i < result.size(); i++) {
				publications.add(Integer.parseInt(result.get(i).get(0)));
			}
			return publications;
		} catch (Exception ex) {
			Logger.getLogger(DLPublication.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

}