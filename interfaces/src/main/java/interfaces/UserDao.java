package interfaces;

import models.User;

public interface UserDao {
    public User findById(int id);
}
