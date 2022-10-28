package com.voidstudio.quickcashreg;

public class LogIn {
  private final LogInActivity logInActivity;
  private final Firebase firebase = new Firebase();

  public LogIn(LogInActivity logInActivity) {
    this.logInActivity = logInActivity;
  }

  private String getUserName(){
   return logInActivity.getUserName();
  }

  private String getPassword(){
    return logInActivity.getPassword();
  }


  /**
   * Checks if user that is logging in is an employee
   *
   * @return True if the user is an employee
   */
  protected boolean isEmployee() {
    if (firebase.getUserType(getUserName()).equals("Employee")) {
      LogInActivity.employee = true;
    } else {
      LogInActivity.employee = false;
    }
    return LogInActivity.employee;
  }

  private boolean isEmptyUsername(){
    return getUserName().isEmpty();
  }

  private boolean isEmptyPassword(){
    return getPassword().isEmpty();
  }


  /**
   * Checks if there is empty fields
   * @return true if there is an empty field
   */
  protected boolean emptyCredentials(){
    if(isEmptyPassword()||isEmptyUsername()){
      return true;
    }
    else return false;
  }

  /**
   *
   * @param username entered username
   * @return true if username exists in the database
   */
  protected boolean existingUser(String username){
    //This method is identical to method in register, Consider refactor
    return firebase.existingUser(username);
  }

  /**
   * Checks if the entered password matches the password associated to the user
   * @param password password entered by the user about to log in
   * @return boolean that is true if the password matches the password associated to the user
   */
  protected boolean passwordMatch(String password){

    if(firebase.checkIfPasswordMatches(getUserName(),password)) return true;
    else return false;
  }

}