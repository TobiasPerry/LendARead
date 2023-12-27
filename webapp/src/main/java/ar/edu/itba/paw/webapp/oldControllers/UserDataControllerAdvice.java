package ar.edu.itba.paw.webapp.oldControllers;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Objects;

@ControllerAdvice
public class UserDataControllerAdvice {

    UserService userService;

    @Autowired
    public UserDataControllerAdvice(UserService userService) {
        this.userService = userService;
    }
    @ModelAttribute("currentUser")
    public User getCurrentUser() throws UserNotFoundException {
        if(Objects.equals(userService.getCurrentUser(), "anonymousUser")){
            return null;
        }
        return userService.getUser(userService.getCurrentUser());
    }
}
