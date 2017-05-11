import java.util.*;  //for ArrayList

/**
 * 
 */
public class SpeakingRequestManager{
   private ResearchDb db;
   
   /**
    * 
    */
   public SpeakingRequestManager(){
      db = new ResearchDb();
   }
   
   /**
    * 
    */
	public boolean addRequest(String name, String email, ArrayList<String> recipients, String msg) {
		boolean success = true;
		String fID;

		ArrayList<String> authors = new ArrayList<String>();
		ArrayList<String> params = getParamArrayList(name,email,msg);

		// create query to insert just name, email, msg, facId fields
		String query1 = "INSERT INTO speaking_requests (requesterName, email, request, facultyId) VALUES(?,?,?,?)";
		String query2 = "SELECT id FROM faculty WHERE CONCAT(fName,' ',lName) = ?";

		try {

			// start transaction
			db.startTrans();
			// for each author
			for (String recipient : recipients) {

				// get author's faculty id
				authors.add(recipient);
				fID = db.getData(query2, authors, false).get(0).get(0);

				params.add(fID);

				// call db setData with query and ArrayList of values
				db.setData(query1, params);

			}
			// end transaction
			db.endTrans();
		} catch (DLException e) {
			try {
				db.rollbackTrans();
			} catch (DLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			success = false;
		}

		return success;
	}
   
   /**
    * 
    */
	public boolean addRequest(String name, String email, String phone, ArrayList<String> recipients, String msg) {
		boolean success = true;
		String fID;

		ArrayList<String> authors = new ArrayList<String>();
		ArrayList<String> params = getParamArrayList(name,email,phone,msg);

		// create query to insert just name, email, msg, facId fields
		String query1 = "INSERT INTO speaking_requests (requesterName, email, phone, request, facultyId) VALUES(?,?,?,?,?)";
		String query2 = "SELECT id FROM faculty WHERE CONCAT(fName,' ',lName) = ?";

		try {

			// start transaction
			//db.startTrans();
			// for each author
			for (String recipient : recipients) {
				// get author's faculty id
				authors.add(recipient);
				fID = db.getData(query2, authors, false).get(0).get(0);

				params.add(fID);

				// call db setData with query and ArrayList of values
				db.setData(query1, params);
				params.remove(4);
				authors.remove(0);
			}
			// end transaction
			//db.endTrans();
		} catch (DLException e) {
//			try {
//				db.rollbackTrans();
//			} catch (DLException e1) {
//				e1.printStackTrace();
//			}
			e.printStackTrace();
			success = false;
		}

		return success;
	}
   
   /**
    * 
    */
	public ArrayList<SpeakingRequest> getRequestsByFaculty(int facId) {

		String query = "select * from speaking_requests where facultyId = ?";
		ArrayList<String> id = new ArrayList<String>();
		ArrayList<ArrayList<String>> speakingRequests = new ArrayList<ArrayList<String>>();
		ArrayList<SpeakingRequest> speakingRequestsList = new ArrayList<SpeakingRequest>();
		id.add(Integer.toString(facId));

		try {
			// call db getData to retrieve speaking requests for the given faculty
			speakingRequests = db.getData(query, id, false);
			// go through returned data, creating an ArrayList of SpeakingRequests
			for (ArrayList<String> arrayList : speakingRequests) {
				SpeakingRequest speakingRequest = new SpeakingRequest(arrayList.get(1), arrayList.get(2),
						arrayList.get(3));
				speakingRequestsList.add(speakingRequest);
			}

		} catch (DLException e) {
			e.printStackTrace();
		}

		return speakingRequestsList;
	}
   
   /**
    * Fills an ArrayList with a series of Strings
    * @return  an ArrayList containing the given Strings
    */
   private ArrayList<String> getParamArrayList(String... params){
      ArrayList<String> paramList = new ArrayList<String>();
      for(String param : params){
         paramList.add(param);
      }
      return paramList;
   }
}  //end SpeakingRequestManager
