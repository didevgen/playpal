package dao.contract;

import models.User;

import java.security.NoSuchAlgorithmException;

public interface UserDAO extends CRUD<User>{
    /**
     * Retrieves user from the db by login and password
     * @param login
     * @param password - must be non-hashed string
     * @return
     * @throws NoSuchAlgorithmException
     */
    User getUserByLoginAndPassword(String login, String password) throws NoSuchAlgorithmException;
}
