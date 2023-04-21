package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class PawUserDetailsService implements UserDetailsService {

    private final UserService userService;
    @Autowired
    public PawUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final User user = userService.getUser(s).orElseThrow(()->new UsernameNotFoundException("Not user for" + s));

        //TODO HACER QUE TENGA LOS ROLES QUE SE NECESITAN BIEN LOS DEBEMOS MAPPEAR EN BD
        final Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_EDITOR"));
        authorities.add(new SimpleGrantedAuthority("ROLE_USER_ADMIN"));

       return new PawUserDetails(user.getEmail(),user.getPassword(),null);
    }
}
