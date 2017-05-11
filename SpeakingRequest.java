/**
 * Represents a user's message to a faculty member with sender's name, email, and potentially
 * phone number along with the message
 */
public class SpeakingRequest{
   private String requesterName = "";
   private String requesterEmail = "";
   private String requesterPhone = "";
   private String msg = "";
   private static final String PHONE_FORMAT = "%s-%s-%s";
   
   /**
    * Constructor initializing minimum required message details
    * @param _name   the name of the message sender
    * @param _email  the email of the message sender
    * @param _msg    the message
    */
   public SpeakingRequest(String _name, String _email, String _msg){
      requesterName = _name;
      requesterEmail = _email;
      msg = _msg;
   }
   
   /**
    * Constructor initializing all message details
    * @param _name   the name of the message sender
    * @param _email  the email of the message sender
    * @param _msg    the message
    * @param _phone  the phone number of the sender
    */
   public SpeakingRequest(String _name, String _email, String _msg, String _phone){
      requesterName = _name;
      requesterEmail = _email;
      msg = _msg;
      requesterPhone = String.format(PHONE_FORMAT, _phone.substring(0,3), _phone.substring(3,6), _phone.substring(6,10));
   }
   
   /**
    * Accessor for sender name
    * @return message sender name
    */
   public String getRequesterName(){
      return requesterName;
   }
   
   /**
    * Accessor for sender email
    * @return message sender email
    */
   public String getRequesterEmail(){
      return requesterEmail;
   }
   
   /**
    * Accessor for sender phone number
    * @return message sender's phone number
    */
   public String getRequesterPhone(){
      return requesterPhone;
   }
   
   /**
    * Accessor for the message
    * @return the message
    */
   public String getMsg(){
      return msg;
   }
}  //end SpeakingRequest