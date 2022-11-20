package log.in.register;

public class ValidateLogIn implements IValidate {

  private String password;
  private String username;
  private String message;

  public ValidateLogIn(String username, String password){
    this.username = username;
    this.password = password;
  }

  @Override
  public boolean validate() {
    if(emptyCredentials()){
      setMessage(LogInRegisterConstants.EMPTY_CREDENTIALS);
      return false;
    }
    else if(!userExists()){
      setMessage(LogInRegisterConstants.USER_DOES_NOT_EXIST);
      return false;
    }
    else if(passwordMatch()){
      setMessage(LogInRegisterConstants.SUCCESS);
      return true;
    }
    else{
      setMessage(LogInRegisterConstants.INCORRECT_PASSWORD);
      return false;
    }
  }

  @Override
  public String getUserType(String username){
    return firebase.getUserType(username);
  }

  /**
   * Checks if there is empty fields
   * @return true if there is an empty field
   */
  private boolean emptyCredentials(){
    return password.isEmpty() || username.isEmpty();
  }
  /**
   *
   * @return true if username exists in the database
   */
  private boolean userExists(){
    //This method is identical to method in register, Consider refactor
    return firebase.existingUser(username);
  }
  /**
   * Checks if the entered password matches the password associated to the user
   * @return boolean that is true if the password matches the password associated to the user
   */
  private boolean passwordMatch(){
    return firebase.getPassword(username).equals(password);
  }

  public String getMessage() {
    return message;
  }

  private void setMessage(String message) {
    this.message = message;
  }







}
