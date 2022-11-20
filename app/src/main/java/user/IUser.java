package user;

import android.location.Location;

public interface IUser {
  public IUser getInstance();
  public String getUsername();
  public String getPassword();
  public String getEmail();
  public Location getLocation();
  public void setUsername(String username);
  public void setPassword(String password);
  public void setEmail(String email);
  public void setLocation(Location location);

  public void save();
}
