package dao.realization.qdsl;

import dao.contract.UserDAO;
import models.entities.User;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class QDslUserDao implements UserDAO{
    @Override
    public User getUserByLoginAndPassword(String login, String password) throws NoSuchAlgorithmException {
        return null;
    }

    @Override
    public User insert(User obj) {
        return null;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public void update(User obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
