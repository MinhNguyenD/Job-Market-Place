package users;

import com.google.android.gms.tasks.Task;

public abstract class User {

  protected String username;
  protected String password;
  protected String email;

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

  public void setUsername(String username){
    this.username = username;
  }

  public void setPassword(String password){
    this.password = password;
  }


  public void setEmail(String email){
    this.email = email;
  }

  abstract Task<Void> search();

  abstract boolean validate();






}
