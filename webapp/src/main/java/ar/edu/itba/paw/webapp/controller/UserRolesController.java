package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.webapp.auth.PawUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;

@Controller
public class UserRolesController {

    private final UserService userService;

    @Autowired
    public UserRolesController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/changeRole",method = RequestMethod.POST)
    public ModelAndView changeRole(HttpServletRequest request){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.changeRole(((UserDetails)auth.getPrincipal()).getUsername(),Behaviour.LENDER);

        HashSet<GrantedAuthority> actualAuthorities = new HashSet<>();
        actualAuthorities.add(new SimpleGrantedAuthority("ROLE_"+ Behaviour.LENDER));
        Authentication newAuth = new
                UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), actualAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return new ModelAndView("redirect:" + request.getHeader("Referer"));
    }


}
