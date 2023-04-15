package com.devtest.user.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordValidatorTest {

  @InjectMocks
  private PasswordValidator passwordValidator;

  @Test
  public void testIsValidPassword_withEmptyPwd_ReturnFalse() {

    // Input
    String password = "";
    // Invoke
    boolean resultIsValid = passwordValidator.isValid(password, null);
    // Assert
    assertFalse(resultIsValid);
  }

  @Test
  public void testIsValidPassword_withLessThan8CharsPwd_ReturnFalse() {

    // Input
    String lessThan8CharPassword = "Abc#123";
    // Invoke
    boolean resultIsValid = passwordValidator.isValid(lessThan8CharPassword, null);
    // Assert
    assertFalse(resultIsValid);
  }

  @Test
  public void testIsValidPassword_withoutDigit_ReturnFalse() {

    // Input
    String missingDigitPassword = "Abcdefgh";
    // Invoke
    boolean resultIsValid = passwordValidator.isValid(missingDigitPassword, null);
    // Assert
    assertFalse(resultIsValid);
  }

  @Test
  public void testIsValidPassword_withoutCaptialized_ReturnFalse() {

    // Input
    String missingCaptializedCharPwd = "abcdefgh12";
    // Invoke
    boolean resultIsValid = passwordValidator.isValid(missingCaptializedCharPwd, null);
    // Assert
    assertFalse(resultIsValid);
  }

  @Test
  public void testIsValidPassword_withoutSpecialChar_ReturnFalse() {

    // Input
    String missingSpecialCharPwd = "Abcdefgh12";
    // Invoke
    boolean resultIsValid = passwordValidator.isValid(missingSpecialCharPwd, null);
    // Assert
    assertFalse(resultIsValid);
  }

  @Test
  public void testIsValidPassword_withValidPassword_Succeed() {

    // Input
    String validPassword = "Abcd#123";
    // Invoke
    boolean resultIsValid = passwordValidator.isValid(validPassword, null);
    // Assert
    assertTrue(resultIsValid);
  }
}
