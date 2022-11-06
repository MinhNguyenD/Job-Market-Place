package users;

import android.location.Location;

import com.google.android.gms.tasks.Task;

public abstract class User {

  protected String username;
  protected String password;
  protected String email;
  private Location location;

  public User(){

  }

  public String getUsername(){
    return username;
  }

  public String getPassword(){
    return password;
  };

  public String getEmail(){
    return email;
  };

  public Location getLocation(){
    return location;
  }

  public void setUsername(String username){
    this.username = username;
  }

  public void setPassword(String password){
    this.password = password;
  }

  public void setLocation(Location location){
    this.location = location;
  }


  public void setEmail(String email){
    this.email = email;
  }

  abstract Task<Void> search();

  abstract boolean validate();

  public abstract void setJob(String jobName, String jobWage, String jobTag, String location);

  abstract User getInstance();



}
