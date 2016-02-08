package dao.realization.jpa;

import dao.contract.UserDAO;
import models.entities.Field;
import models.entities.User;
import play.db.jpa.JPA;
import services.UserService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class JpaUserDao implements UserDAO{
    EntityManager manager = JPA.em();
    @Override
    public User insert(User obj) {
        manager.persist(obj);
        return obj;
    }

    @Override
    public User get(int id) {
        return manager.find(User.class, id);
    }

    @Override
    public void update(User obj) {
        manager.merge(obj);
    }

    @Override
    public void delete(int id) {
        manager.remove(manager.find(User.class,id));
    }

    @Override
    public List<User> getAll() {
        return manager.createQuery("Select e from User e").getResultList();
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) throws NoSuchAlgorithmException {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<User> q = cb.createQuery(User.class);
        Root<User> c = q.from(User.class);
        q.select(c).where(cb.equal(c.get("login"), login)).where(cb.equal(c.get("password"),
                new UserService().md5(password)));
        List<User> res = JPA.em().createQuery(q).getResultList();
        if (res.isEmpty()) {
            return null;
        } else {
            return res.get(0);
        }
    }
}
