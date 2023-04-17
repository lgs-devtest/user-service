package com.devtest.user.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

  @Override
  public boolean isValid(final String valueToValidate, final ConstraintValidatorContext context) {

    if (valueToValidate == null || valueToValidate.length() < 8) {
      return false;
    }

    if (!valueToValidate.matches(".*?[0-9].*")
        || !valueToValidate.matches(".*?[A-Z].*")
        || !valueToValidate.matches(".*?[_#$%.].*")) {
      return false;
    }

    return true;
  }
}
