package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserProfileViewController {
    final UserService userService;

    public UserProfileViewController(UserService userService, ImageService imageService) {
        this.userService = userService;
    }

    @RequestMapping("/user/{id}")
    public ModelAndView userProfileView(@PathVariable(name = "id") int id) throws UserNotFoundException {
        UserImpl user = userService.getUserById(id);

        return new ModelAndView("/views/userProfileView")
                .addObject("user", user)
                .addObject("isCurrent", userService.isCurrent(id));
    }
}
