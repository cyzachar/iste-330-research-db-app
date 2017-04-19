/**
 * Represents a faculty record with an id, first & last name, password, & email
 * @author Team 17
 */
public class Faculty {

	private int id;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
   
   /**
    * Default constructor
    */
	public Faculty() {
		super();
	}
   
   /**
    * Initializes all attributes
    * @param id         the id of the faculty member
    * @param firstName  the first name of the faculty member
    * @param lastName   the last name fo the faculty member
    * @param password   the faculty member's password
    * @param email      the faculty member's email address
    */
	public Faculty(int id, String firstName, String lastName, String password, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
	}
   
   /**
    * Accessor for id
    * @return the faculty member's id
    */
	public int getId() {
		return id;
	}
   
   /**
    * Mutator for id
    * @param id   the new id for the faculty member
    */
	public void setId(int id) {
		this.id = id;
	}
   
   /**
    * Accessor for first name
    * @return the faculty member's first name
    */
	public String getFirstName() {
		return firstName;
	}
   
   /**
    * Mutator for first name
    * @param firstName   the new first name for the faculty member
    */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
   
   /**
    * Accessor for last name
    * @return the faculty member's last name
    */
	public String getLastName() {
		return lastName;
	}
   
   /**
    * Mutator for last name
    * @param lastName   the new last name for the faculty member
    */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
   
   /**
    * Accessor for password
    * @return the faculty member's password
    */
	public String getPassword() {
		return password;
	}
   
   /**
    * Mutator for password
    * @param password   the new password for the faculty member
    */
	public void setPassword(String password) {
		this.password = password;
	}
   
   /**
    * Accessor for email
    * @return the faculty member's email
    */
	public String getEmail() {
		return email;
	}
   
   /**
    * Mutator for email
    * @param email   the new email for the faculty member
    */
	public void setEmail(String email) {
		this.email = email;
	}
   
   /**
    * Returns a string describing faculty details
    * FOR TESTING PURPOSES ONLY
    * @return a string describing faculty id, name, email, & password
    */
   public String toString(){
      return "Id: " + id
               + "\nFirst name: " + firstName
               + "\nLast name: " + lastName
               + "\nPassword: " + password
               + "\nEmail: " + email;
   }
   
}  //end Faculty class
