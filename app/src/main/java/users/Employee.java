package users;

import com.google.android.gms.tasks.Task;

public class Employee extends User {

  public Employee(String username, String email, String password){
    this.username = username;
    this.email = email;
    this.password = password;
  }

 protected Task<Void> search(){
   return null;
 }
}
