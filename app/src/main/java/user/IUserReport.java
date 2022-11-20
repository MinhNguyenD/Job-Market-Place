package user;

import com.voidstudio.quickcashreg.Firebase;

public interface IUserReport {
  Firebase firebase = Firebase.getInstance();
  public void storeUserInfo();
  public void setUserInfo(String username);
}
