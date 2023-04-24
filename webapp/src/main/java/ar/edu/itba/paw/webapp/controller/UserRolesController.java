package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.webapp.auth.PawUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public void changeROle(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changeRole(userDetails.getUsername(),Behaviour.LENDER);
        Collection<GrantedAuthority> collection = new HashSet<>();
        collection.add(new SimpleGrantedAuthority("ROLE_"+ Behaviour.LENDER));
        UserDetails user = new PawUserDetails(userDetails.getUsername(), userDetails.getPassword(), collection);
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
