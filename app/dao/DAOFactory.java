package dao;

import dao.contract.FieldDAO;
import dao.contract.ResponseDAO;
import dao.contract.UserDAO;
import play.Play;

public abstract class DAOFactory {
    public static final String JPA = "JPA";
    public static final String QUERY_DSL = "QDSL";

    public abstract UserDAO getUserDAO();
    public abstract FieldDAO getFieldDAO();
    public abstract ResponseDAO getResponseDAO();

    public static DAOFactory getDAOFactory() {
        String whichFactory = Play.application().configuration().getString("dao");
        switch (whichFactory) {
            case JPA:
                return new JpaDaoFactory();
            case QUERY_DSL:
                return new QDslDaoFactory();
            default:
                return null;
        }
    }
    public static DAOFactory getDAOFactory(String whichFactory) {
        switch (whichFactory) {
            case JPA:
                return new JpaDaoFactory();
            case QUERY_DSL:
                return new QDslDaoFactory();
            default:
                return null;
        }
    }
}
