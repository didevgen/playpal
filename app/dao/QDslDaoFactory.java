package dao;

import dao.contract.FieldDAO;
import dao.contract.ResponseDAO;
import dao.contract.UserDAO;

public class QDslDaoFactory extends DAOFactory{
    @Override
    public UserDAO getUserDAO() {
        return null;
    }

    @Override
    public FieldDAO getFieldDAO() {
        return null;
    }

    @Override
    public ResponseDAO getResponseDAO() {
        return null;
    }
}
