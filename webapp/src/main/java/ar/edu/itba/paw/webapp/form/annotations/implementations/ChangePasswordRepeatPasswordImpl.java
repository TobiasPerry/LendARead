package ar.edu.itba.paw.webapp.form.annotations.implementations;

import ar.edu.itba.paw.webapp.form.ChangePasswordForm;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.RepeatPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChangePasswordRepeatPasswordImpl implements ConstraintValidator<RepeatPassword, ChangePasswordForm> {

    @Override
    public void initialize(RepeatPassword sumDate) {

    }

    @Override
    public boolean isValid(ChangePasswordForm value, ConstraintValidatorContext constraintValidatorContext) {
        return value.getPassword().equals(value.getRepeatPassword());
    }
}