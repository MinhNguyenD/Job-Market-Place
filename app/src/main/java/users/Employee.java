package users;

import com.google.android.gms.tasks.Task;
import com.voidstudio.quickcashreg.Firebase;

public class Employee extends User {

    // For observing employer
    protected Employer employer;
    public boolean newJobAlert;
    public boolean newJobSeen;

    public Firebase firebase;
    private static User employee;
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
    public void setJob(String jobName, String jobWage, String jobTag){

    }
    public User getInstance(){
        if(employee == null){
            employee = new Employer(username, email, password);
        }
        return employee;
    }

    public void update() {
        //Update newJobAlert to true
        newJobAlert = true;
    }
}
