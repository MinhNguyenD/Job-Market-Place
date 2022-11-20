package user;

public class EmployerReport implements IUserReport{

  IUser employer;

  public EmployerReport(IUser employer){
    this.employer = employer;
  }

  @Override
  public void storeUserInfo() {
    firebase.addUser(employer.getUsername(), employer.getPassword(),
            employer.getEmail(), UserConstants.EMPLOYER);
  }

  @Override
  public void setUserInfo(String username) {
    employer.setUsername(username);
    employer.setEmail(firebase.getEmailAddress(username));
    employer.setPassword(firebase.getPassword(username));
  }


}
