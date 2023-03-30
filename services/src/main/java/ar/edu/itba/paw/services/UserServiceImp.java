package ar.edu.itba.paw.services;

import interfaces.UserDao;
import interfaces.UserService;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserDao us;

    @Override
    public User searchById(int id) {
       return us.findById(id);
    }
}
