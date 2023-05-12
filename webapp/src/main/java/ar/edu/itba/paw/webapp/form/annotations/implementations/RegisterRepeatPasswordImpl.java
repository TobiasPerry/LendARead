package ar.edu.itba.paw.webapp.form.annotations.implementations;

import ar.edu.itba.paw.webapp.form.AddAssetForm;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.RepeatPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegisterRepeatPasswordImpl implements ConstraintValidator<RepeatPassword, RegisterForm> {

    @Override
    public void initialize(RepeatPassword sumDate) {

    }

    @Override
    public boolean isValid(RegisterForm value, ConstraintValidatorContext constraintValidatorContext) {
        return value.getPassword().equals(value.getRepeatPassword());
    }
}