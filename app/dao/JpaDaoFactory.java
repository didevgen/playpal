package dao;

import dao.contract.FieldDAO;
import dao.contract.ResponseDAO;
import dao.contract.UserDAO;
import dao.realization.jpa.JpaFieldDao;
import dao.realization.jpa.JpaResponseDao;
import dao.realization.jpa.JpaUserDao;

public class JpaDaoFactory extends DAOFactory {
    @Override
    public UserDAO getUserDAO() {
        return new JpaUserDao();
    }

    @Override
    public FieldDAO getFieldDAO() {
        return new JpaFieldDao();
    }

    @Override
    public ResponseDAO getResponseDAO() {
        return new JpaResponseDao();
    }
}
