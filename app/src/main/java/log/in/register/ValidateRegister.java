package log.in.register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateRegister implements IValidate{


  private String email;
  private String confirmPassword;
  private String selectedRole;
  private String username;
  private String password;
  private String message;

  public ValidateRegister(String username, String password, String email, String confirmPassword, String selectedRole){
    this.email = email;
    this.confirmPassword = confirmPassword;
    this.selectedRole = selectedRole;
    this.username = username;
    this.password = password;
  }

  /**
   Check if email address is valid
   **/
  private boolean isValidEmailAddress(String emailAddress) {
        /*
            Reference: OWASP Email Regex
            https://owasp.org/www-community/OWASP_Validation_Regex_Repository
         */
    Pattern p = Pattern.compile(LogInRegisterConstants.EMAIL_REGEX_PATTERN);
    Matcher m = p.matcher(emailAddress);
    if(m.find()){
      return true;
    }
    return false;
  }

  /**
   Check if password is valid
   **/
  protected boolean isValidPassword(String password) {
    if(password.length() >= 6){
      return true;
    }
    return false;
  }

  /**
   * Check if password matches confirm password
   * @param password entered password
   * @param confirmPassword entered confirmPassword
   * @return true if password.equals(confirmPassword)
   */
  private boolean isValidConfirmPassword(String password, String confirmPassword) {
    if(password.equals(confirmPassword)){
      return true;
    }
    return false;
  }

  /**
   * Checks if username already exists in database
   * @return boolean of userNameExisted, true if username exists
   */
  private boolean userNameExisted(String username){
    return firebase.existingUser(username);
  }

  protected boolean isValidRole(String role) {
    return !role.equals(LogInRegisterConstants.NULL_USERTYPE);
  }


  @Override
  public boolean validate() {
    return false;
  }

  @Override
  public String getUserType(String username) {
    return firebase.getUserType(username);
  }

  @Override
  public String getMessage(){
    return message;
  }

}
