package ar.edu.itba.paw.webapp.form.annotations.implementations;


import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.EmailExistence;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class EmailExistenceImpl implements ConstraintValidator<EmailExistence, String> {

    private final UserService userService;

    @Autowired
    public EmailExistenceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> user = userService.getUser(s);
        return !user.isPresent();
    }
}
