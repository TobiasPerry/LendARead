package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.PasswordResetTokenImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.PasswordResetToken;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    public UserServiceImpl(PasswordEncoder passwordEncoder,UserDao userDao,EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.emailService = emailService;
    }


    @Override
    public Optional<User> getUser(String email) {
        return userDao.getUser(email);
    }

    @Override
    public int createUser(String email,String name,String telephone,String password) {
        userDao.addUser(Behaviour.BORROWER,email,name,telephone,passwordEncoder.encode(password));
        return 0;
    }

    @Override
    public boolean changeRole(String email, Behaviour behaviour) {
        return userDao.changeRole(email,behaviour);
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
