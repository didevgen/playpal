package dao;

import dao.contract.FieldDAO;
import dao.contract.ResponseDAO;
import dao.contract.UserDAO;
import dao.realization.qdsl.QDslFieldDao;
import dao.realization.qdsl.QDslResponseDao;
import dao.realization.qdsl.QDslUserDao;

public class QDslDaoFactory extends DAOFactory{
    @Override
    public UserDAO getUserDAO() {
        return new QDslUserDao();
    }

    @Override
    public FieldDAO getFieldDAO() {
        return new QDslFieldDao();
    }

    @Override
    public ResponseDAO getResponseDAO() {
        return new QDslResponseDao();
    }
}
