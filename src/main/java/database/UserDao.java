package database;

import models.User;

import java.util.Optional;

public class UserDao implements Dao<User> {

    @Override
    public Optional<User> get(long id) {
        return Optional.empty();
    }

    @Override
    public void save(User user) {

    }
}
