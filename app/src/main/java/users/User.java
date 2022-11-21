package users;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.tasks.Task;
import com.voidstudio.quickcashreg.firebase.Firebase;
import com.voidstudio.quickcashreg.Location.ILocation;
import com.voidstudio.quickcashreg.Location.UserLocation;
import com.voidstudio.quickcashreg.firebase.FirebaseConstants;

import java.util.Map;

public abstract class User {

  protected String username;
  protected String password;
  protected String email;
  protected String userType;
  public ILocation locate;
  private static final Firebase firebase = Firebase.getInstance();
  public User(){

  }



  public String getUsername(){
    return username;
  }

  public String getPassword(){
    return password;
  }

  public String getEmail(){
    return email;
  }

  public String getUserType(){
    return userType;
  }

  public void setUsername(String username){
    this.username = username;
  }

  public void setPassword(String password){
    this.password = password;
  }

  public void setEmail(String email){
    this.email = email;
  }


  public void setLocation(Location location){
    locate.setLocation(location);
  }

  public static User getUser(String username){
    User user = null;
    Map<String,String> userDetails = firebase.getUserInfo(username);
    if(userDetails.containsValue(null)) return null;
    String type = userDetails.get("type");
    if(type !=null && type.equals(UserConstants.EMPLOYEE)){
      user = new Employee(userDetails.get(FirebaseConstants.USERNAME),
              userDetails.get(FirebaseConstants.EMAIL),userDetails.get(FirebaseConstants.PASSWORD));
    }
    else if(type!=null){
      user = new Employer(userDetails.get(FirebaseConstants.USERNAME),
              userDetails.get(FirebaseConstants.EMAIL),userDetails.get(FirebaseConstants.PASSWORD));
    }
    return user;
  }

  abstract Task<Void> search();

  abstract boolean validate();

  public abstract void setJob(String jobName, String jobWage, String jobTag);


  abstract User getInstance();


  public Location getLocation() {
    return locate.getMyLocation();
  }

  public double[] getLatLong(){
    return locate.getLatLong();
  }

  public boolean startLocating(Context context){
    locate = new UserLocation(context);
    return locate.getMyLocation() != null;
  }





}
