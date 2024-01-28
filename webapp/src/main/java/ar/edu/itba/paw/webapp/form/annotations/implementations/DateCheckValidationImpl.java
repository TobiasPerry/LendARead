package ar.edu.itba.paw.webapp.form.annotations.implementations;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.DateCheckValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateCheckValidationImpl implements ConstraintValidator<DateCheckValidation, LocalDate> {

    @Override
    public boolean isValid(LocalDate s, ConstraintValidatorContext constraintValidatorContext) {
     if (s == null){
         return false;
     }
     LocalDate today = LocalDate.now();
     return s.isAfter(today) || s.isEqual(today);
    }
}