package user;

public class UserFactory {

  public IUser getUser(String userType){
    if(userType.equals(UserConstants.EMPLOYEE)){
      return new EmployeeSRP();
    }
    else if(userType.equals(UserConstants.EMPLOYER)){
      return new EmployerSRP();
    }
    else return null;
  }



}
