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

    public Employee(String username, String email, String password, Employer employer){
        this.username = username;
        this.email = email;
        this.password = password;
        this.employer = employer;
        firebase = Firebase.getInstance();
    }

    public Employee(String username, String password) {
        this.username = username;
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
            employee = new Employee(username, email, password);
        }
        return employee;
    }
}
