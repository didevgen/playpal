package dao;

import dao.contract.FieldDAO;
import dao.contract.ResponseDAO;
import dao.contract.UserDAO;
import play.Play;

/**
 * Abstract factory pattern implementation
 */
public abstract class DAOFactory {
    public static final String JPA = "JPA";
    public static final String QUERY_DSL = "QDSL"; //Querydsl

    public abstract UserDAO getUserDAO();
    public abstract FieldDAO getFieldDAO();
    public abstract ResponseDAO getResponseDAO();

    /**
     * Gets DaoFactory using the application context parameter
     * @return
     */
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

    /**
     * Gets DaoFactory by customer's parameter
     * @param whichFactory
     * @return
     */
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
