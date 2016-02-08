package dao.contract;

import models.entities.User;

import java.security.NoSuchAlgorithmException;

public interface UserDAO extends CRUD<User>{
    User getUserByLoginAndPassword(String login, String password) throws NoSuchAlgorithmException;
}
