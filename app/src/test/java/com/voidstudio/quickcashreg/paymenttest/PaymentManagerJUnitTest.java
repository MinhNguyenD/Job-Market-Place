package com.voidstudio.quickcashreg.paymenttest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.voidstudio.quickcashreg.payment.IPayment;
import com.voidstudio.quickcashreg.payment.PaymentManager;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;



@RunWith(MockitoJUnitRunner.Silent.class)
public class PaymentManagerJUnitTest {

  static IPayment firstPayment;
  static IPayment secondPayment;
  static IPayment thirdPayment;

  @BeforeClass
  public static void setup() {
    //mock IPayment interface using Mockito framework!
    firstPayment = Mockito.mock(IPayment.class);
    secondPayment = Mockito.mock(IPayment.class);
    thirdPayment = Mockito.mock(IPayment.class);
  }

  @Test
  public void testPaymentSuccessful() {
    Mockito.when(firstPayment.getPaymentAmount()).thenReturn(100);
    Mockito.when(firstPayment.getCreditCardNumber()).thenReturn("5415856900657568");
    Mockito.when(firstPayment.getCCExpiredDate()).thenReturn("2025-02-28");
    PaymentManager paymentManager = new PaymentManager();
    assertTrue(paymentManager.makePayment(firstPayment));
    assertEquals(100, firstPayment.getPaymentAmount());
    assertEquals("5415856900657568", firstPayment.getCreditCardNumber());
    assertEquals("2025-02-28", firstPayment.getCCExpiredDate());
    Mockito.verify(firstPayment, Mockito.atLeastOnce()).getPaymentAmount();
    Mockito.verify(firstPayment, Mockito.atLeastOnce()).getCreditCardNumber();
    Mockito.verify(firstPayment, Mockito.atLeastOnce()).getCCExpiredDate();
  }

  @Test
  public void testPaymentUnsuccessful() {
    Mockito.when(secondPayment.getPaymentAmount()).thenReturn(200);
    Mockito.when(secondPayment.getCreditCardNumber()).thenReturn("4415851234657568");
    Mockito.when(secondPayment.getCCExpiredDate()).thenReturn("2022-02-28");
    PaymentManager paymentManager = new PaymentManager();
    assertFalse(paymentManager.makePayment(secondPayment));
    assertEquals(200, secondPayment.getPaymentAmount());
    assertEquals("4415851234657568", secondPayment.getCreditCardNumber());
    assertEquals("2022-02-28", secondPayment.getCCExpiredDate());
    Mockito.verify(secondPayment, Mockito.atLeastOnce()).getPaymentAmount();
    Mockito.verify(secondPayment, Mockito.atLeastOnce()).getCCExpiredDate();
    Mockito.verify(secondPayment, Mockito.times(1)).getCreditCardNumber();
  }

  @Test
  public void testInvalidCreditCard() {
    Mockito.when(thirdPayment.getPaymentAmount()).thenReturn(300);
    Mockito.when(thirdPayment.getCreditCardNumber()).thenReturn("3415851234657");
    Mockito.when(thirdPayment.getCCExpiredDate()).thenReturn("2026-03-31");
    PaymentManager paymentManager = new PaymentManager();
    assertFalse(paymentManager.makePayment(thirdPayment));
    assertEquals(300, thirdPayment.getPaymentAmount());
    assertEquals("3415851234657", thirdPayment.getCreditCardNumber());
    assertEquals("2026-03-31", thirdPayment.getCCExpiredDate());
    Mockito.verify(thirdPayment, Mockito.atLeastOnce()).getPaymentAmount();
    Mockito.verify(thirdPayment, Mockito.atLeastOnce()).getCreditCardNumber();
    Mockito.verify(thirdPayment, Mockito.atLeastOnce()).getCCExpiredDate();
  }

}
