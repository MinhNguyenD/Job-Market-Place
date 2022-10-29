package users;

import com.google.android.gms.tasks.Task;

public abstract class User {

  protected String username;
  protected String password;
  protected String email;

  public User(){

  }

  protected String getUsername(){
    return username;
  }

  protected String getPassword(){
    return password;
  };

  protected String getEmail(){
    return email;
  };

  protected void setUsername(String username){
    this.username = username;
  }

  protected void setPassword(String password){
    this.password = password;
  };

  protected void setEmail(String email){
    this.email = email;
  }

  abstract Task<Void> search();






}
