package users;

import com.google.android.gms.tasks.Task;
import com.voidstudio.quickcashreg.Firebase;

public class Employee extends User {

  Firebase firebase;

  public Employee(String username, String email, String password){
    this.username = username;
    this.email = email;
    this.password = password;
    firebase = Firebase.getInstance();
  }

 protected Task<Void> search(){
   return null;
 }

 protected boolean validate(){
    return false;

 }
}
