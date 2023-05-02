package ar.edu.itba.paw.webapp.form.annotations.implementations;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.Email;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.ValidToken;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidTokenImpl implements ConstraintValidator<ValidToken, String> {
    private final UserService userService;

    @Autowired
    public ValidTokenImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String token, ConstraintValidatorContext constraintValidatorContext) {
       return userService.isTokenValid(token);
    }
}