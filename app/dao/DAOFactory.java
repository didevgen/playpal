package dao;

import dao.contract.FieldDAO;
import dao.contract.ResponseDAO;
import dao.contract.UserDAO;

public abstract class DAOFactory {
    public static final String JPA = "JPA";
    public abstract UserDAO getUserDAO();
    public abstract FieldDAO getFieldDAO();
    public abstract ResponseDAO getResponseDAO();

    public static DAOFactory getDAOFactory(String whichFactory) {
        switch (whichFactory) {
            case JPA:
                return new JpaDaoFactory();
            default:
                return null;
        }
    }
}
