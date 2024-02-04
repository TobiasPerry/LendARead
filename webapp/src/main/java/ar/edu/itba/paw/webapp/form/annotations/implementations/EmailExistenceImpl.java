package ar.edu.itba.paw.webapp.form.annotations.implementations;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.EmailExistence;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailExistenceImpl implements ConstraintValidator<EmailExistence, String> {

    private final UserService userService;

    @Autowired
    public EmailExistenceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            User user = userService.getUser(s);
            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }
}