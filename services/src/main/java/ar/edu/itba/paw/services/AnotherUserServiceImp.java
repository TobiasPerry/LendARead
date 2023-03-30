package ar.edu.itba.paw.services;

import interfaces.UserService;
import models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AnotherUserServiceImp implements UserService {
    @Override
    public User searchById(int id) {
        return null;
    }
}
