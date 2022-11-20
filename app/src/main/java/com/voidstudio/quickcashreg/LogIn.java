package com.voidstudio.quickcashreg;

import static user.UserConstants.EMPLOYEE;

import android.widget.Toast;

import com.google.android.gms.tasks.Task;

import log.in.register.IValidate;
import log.in.register.ValidateLogIn;
import user.EmployeeReport;
import user.EmployerReport;
import user.IUser;
import user.UserConstants;
import user.UserFactory;

public class LogIn {
  private final LogInActivity logInActivity;

  private IValidate validator;
  protected IUser user;
  private String userType;

  public LogIn(LogInActivity logInActivity) {
    this.logInActivity = logInActivity;
  }


  private String getUserName(){
   return logInActivity.getUserName();
  }

  private String getPassword(){
    return logInActivity.getPassword();
  }

  protected Task<Void> getAlertMessage(){
    Toast.makeText(logInActivity, validator.getMessage(), Toast.LENGTH_SHORT).show();
    return null;
  }

  /**
   * Checks if user that is logging in is an employee
   *
   * @return True if the user is an employee
   */
  protected boolean isEmployee() {
    if(validator.getUserType(getUserName()).equals(EMPLOYEE)){
      userType = EMPLOYEE;
      return true;
    }
    else{
      userType = UserConstants.EMPLOYER;
      return false;
    }
  }

  /**
   * Log In method
   * Sets user info when they log in.
   * @return true if log in is successful.
   */
  protected boolean logIn(){
    validator = new ValidateLogIn(getUserName(),getPassword());
    if(validator.validate()){
      userType = validator.getUserType(getUserName());
      if(userType.equals(EMPLOYEE)){
        user = new UserFactory().getUser("Employee");
        new EmployeeReport(user).setUserInfo(getUserName());
      }
      else{
        user = new UserFactory().getUser("Employer");
        new EmployerReport(user).setUserInfo(getUserName());
      }
    }
    return validator.validate();
  }

  protected void activateUser(String username){
    userType = validator.getUserType(username);
    if(userType.equals(EMPLOYEE)){
      user = new UserFactory().getUser("Employee");
      new EmployeeReport(user).setUserInfo(getUserName());
    }
    else{
      user = new UserFactory().getUser("Employer");
      new EmployerReport(user).setUserInfo(getUserName());
    }
  }





}