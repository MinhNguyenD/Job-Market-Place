package com.voidstudio.quickcashreg.payment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * used from assignment 3 q1
 */
public class PaymentManager{

  String MASTERCARD_STARTING_DIGIT = "5";
  String VISA_STARTING_DIGIT = "4";
  int CARD_LENGTH_SIXTEEN = 16;
  int CARD_LENGTH_THIRTEEN = 13;


  /**
   * @param payment a payment
   * @return true if the payment is valid
   */
  public boolean makePayment(IPayment payment){
    Date creditCardExpireDate = convertToDate(payment.getCCExpiredDate());
    Date date = new Date();
    int paymentAmount = payment.getPaymentAmount();

    if(paymentAmount<=0) return false;

    else if(creditCardExpireDate.compareTo(date)<0){
      return false;
    }
    String creditCardNumber = payment.getCreditCardNumber();
    if(isSupportedCard(creditCardNumber)) return true;
    else return false;
  }

  public boolean isSupportedCard(String creditCardNumber) {
    // check the validity of a credit card number
    int length = creditCardNumber.length();
    char[] creditCardArray = creditCardNumber.toCharArray();
    String firstDigit = String.valueOf(creditCardArray[0]);
    if(length == CARD_LENGTH_SIXTEEN && (firstDigit.equals(MASTERCARD_STARTING_DIGIT))){
      return true;
    }
    else if((length == CARD_LENGTH_THIRTEEN || length  == CARD_LENGTH_SIXTEEN) &&
            firstDigit.equals(VISA_STARTING_DIGIT)){
      return true;
    }
    else{
      return false;
    }
  }

  public Date convertToDate(String dateString){
    //convert the date string to a date class!
    if(dateString == null) return null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
    Date date = new Date();
    try {
      date = dateFormat.parse(dateString);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

}
