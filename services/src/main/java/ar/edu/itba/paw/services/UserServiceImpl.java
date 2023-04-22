package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder,UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }


    @Override
    public Optional<User> getUser(String email) {
        return userDao.getUser(email);
    }

    @Override
    public int createUser(String email,String name,String telephone,String password) {
        userDao.addUser("lender",email,name,telephone,passwordEncoder.encode(password));
        return 0;
    }
}
