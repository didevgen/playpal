package dao.realization.qdsl;

import com.mysema.query.jpa.EclipseLinkTemplates;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import dao.contract.UserDAO;
import models.QUser;
import models.User;
import play.db.jpa.JPA;
import services.UserService;

import javax.persistence.EntityManager;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class QDslUserDao implements UserDAO{
    EntityManager manager = JPA.em();
    @Override
    public User getUserByLoginAndPassword(String login, String password) throws NoSuchAlgorithmException {
        QUser user = QUser.user;
        JPQLQuery query = new JPAQuery(manager, EclipseLinkTemplates.DEFAULT);
        User result = query.from(user)
                .where(user.login.eq(login).and(user.password.eq(new UserService().md5(password))))
                .uniqueResult(user);
        return result;
    }

    @Override
    public User insert(User obj) {
        manager.persist(obj);
        return obj;
    }

    @Override
    public User get(int id) {
        QUser user = QUser.user;
        JPQLQuery query = new JPAQuery(manager, EclipseLinkTemplates.DEFAULT);
        User result = query.from(user)
                .where(user.userId.eq(id))
                .uniqueResult(user);
        return result;
    }

    @Override
    public void update(User obj) {
        manager.merge(obj);
    }

    @Override
    public void delete(int id) {
        QUser user = QUser.user;
        new JPADeleteClause(manager, user).where(user.userId.eq(id)).execute();
    }

    @Override
    public List<User> getAll() {
        QUser user = QUser.user;
        JPQLQuery query = new JPAQuery (manager, EclipseLinkTemplates.DEFAULT);
        return query.from(user).list(user);
    }
}
