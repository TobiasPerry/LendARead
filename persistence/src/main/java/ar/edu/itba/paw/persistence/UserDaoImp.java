package ar.edu.itba.paw.persistence;

import interfaces.UserDao;
import models.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class UserDaoImp implements UserDao {
    @Override
    public User findById(int id) {
        return new User("Laucha");
    }
}
