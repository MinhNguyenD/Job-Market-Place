package log.in.register;

import com.voidstudio.quickcashreg.Firebase;

public interface IValidate {
  Firebase firebase = Firebase.getInstance();
  public boolean validate();
  public String getUserType(String username);
  public String getMessage();




}
