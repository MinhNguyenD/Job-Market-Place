package users;

import com.google.android.gms.tasks.Task;

public class Employer extends User {


  public Employer(String username, String email, String password){
    this.username = username;
    this.email = email;
    this.password = password;
  }

  protected Task<Void> search(){
    return null;
  }

  protected boolean validate(){
    return false;
  }



}
