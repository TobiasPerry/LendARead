package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.PasswordResetTokenImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.PasswordResetToken;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    private final EmailService emailService;
    @Autowired
    public UserServiceImpl(final PasswordEncoder passwordEncoder,final UserDao userDao,final EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.emailService = emailService;
    }


    @Override
    public User getUser(final String email) throws UserNotFoundException {
        Optional<User> user = userDao.getUser(email);
        if (!user.isPresent())
            throw new UserNotFoundException("The user was not found");
        return user.get();
    }

    @Override
    public User createUser(String email,String name,String telephone,String password) {
      return  userDao.addUser(Behaviour.BORROWER,email,name,telephone,passwordEncoder.encode(password));
    }

    @Override
    public void changeRole(String email, Behaviour behaviour) throws UserNotFoundException {
        boolean changed = userDao.changeRole(email,behaviour);
        if(!changed)
            throw new UserNotFoundException("The user was not founded");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HashSet<GrantedAuthority> actualAuthorities = new HashSet<>();
        actualAuthorities.add(new SimpleGrantedAuthority("ROLE_"+ behaviour.toString()));
        Authentication newAuth = new
                UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), actualAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @Override
    public String getCurrentUser() {
        return ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getCurrentRoles() {
        return  SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    @Override
    public boolean createChangePasswordToken(String email){
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetTokenImpl(token,email, LocalDate.now().plusDays(1));
        Map<String,Object> variables = new HashMap<>();
        variables.put("token",passwordResetToken.getToken());
        emailService.sendEmail(email,"Change password",emailService.lenderMailFormat(variables,"ForgotPasswordEmailTemplate.html"));
        return userDao.setForgotPasswordToken(passwordResetToken);
    }

    @Override
    public boolean changePassword(String token,String password){
        Optional<PasswordResetToken> passwordResetToken = userDao.getPasswordRestToken(token);
        if(!passwordResetToken.isPresent())
            return false;
        if(!isTokenValid(token))
           return false;
        userDao.deletePasswordRestToken(token);
        return userDao.changePassword(passwordResetToken.get().getUser(),passwordEncoder.encode(password));
    }
    @Override
    public boolean isTokenValid(String token){
        Optional<PasswordResetToken> passwordResetToken = userDao.getPasswordRestToken(token);
        return passwordResetToken.map(resetToken -> resetToken.getExpiryDate().isAfter(LocalDate.now())).orElse(false);
    }
}
