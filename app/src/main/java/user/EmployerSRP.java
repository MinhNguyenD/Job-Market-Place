package user;

import android.location.Location;

public class EmployerSRP implements IUser{

  private IUser user;
  private String username;
  private String password;
  private String email;
  private Location location;

  @Override
  public IUser getInstance() {
    if(user == null){
      user = new EmployerSRP();
    }
    return user;
  }

  @Override
  public String getUsername() {
    return username;
  }
  @Override
  public void setUsername(String username){
    this.username = username;
  }

  @Override
  public String getPassword() {
    return password;
  }
  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getEmail() {
    return email;
  }
  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public Location getLocation() {
    return location;
  }
  @Override
  public void setLocation(Location location) {
    this.location = location;
  }

  @Override
  public void save(){
    new EmployerReport(this).storeUserInfo();
  }
}
