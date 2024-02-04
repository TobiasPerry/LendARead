package ar.edu.itba.paw.webapp.form.annotations.implementations;


import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.EmailNotExistence;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailNotExistenceImpl implements ConstraintValidator<EmailNotExistence, String> {

    private final UserService userService;

    @Autowired
    public EmailNotExistenceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            User user = userService.getUser(s);
            return false;
        } catch (UserNotFoundException e) {
            return true;
        }
    }
}
