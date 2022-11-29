package com.voidstudio.quickcashreg.payment;

/**
 * Took this from assignment 3 q 1
 */
public interface IPayment {

  public int getPaymentAmount();

  public String getCreditCardNumber();

  public String getCCExpiredDate();

}
