package user;

public class EmployeeReport implements IUserReport {

  IUser employee;
  public EmployeeReport(IUser employee){
    this.employee = employee;
  }

  @Override
  public void storeUserInfo() {
    //Firebase code for storing info
    firebase.addUser(employee.getUsername(), employee.getPassword(),
            employee.getEmail(), UserConstants.EMPLOYEE);
  }

  @Override
  public void setUserInfo(String username) {
    employee.setUsername(username);
    employee.setEmail(firebase.getEmailAddress(username));
    employee.setPassword(firebase.getPassword(username));
  }


}
