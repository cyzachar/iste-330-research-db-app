public class SpeakingRequest{
   private String requesterName;
   private String requesterEmail;
   private String requesterPhone;
   private String msg;
   private static final String PHONE_FORMAT = "%s-%s-%s";
   
   public SpeakingRequest(String _name, String _email, String _msg){
      requesterName = _name;
      requesterEmail = _email;
      msg = _msg;
   }
   
   public SpeakingRequest(String _name, String _email, String _msg, String _phone){
      requesterName = _name;
      requesterEmail = _email;
      msg = _msg;
      requesterPhone = String.format(PHONE_FORMAT, _phone.substring(0,3), _phone.substring(3,6), _phone.substring(6,10));
   }
   
   public String getRequesterName(){
      return requesterName;
   }
   
   public String getRequesterEmail(){
      return requesterEmail;
   }
   
   public String getRequesterPhone(){
      return requesterPhone;
   }
   
   public String getMsg(){
      return msg;
   }
}