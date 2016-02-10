package dao.realization.jpa;

import dao.contract.ResponseDAO;
import models.entities.Response;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;
import java.util.List;

public class JpaResponseDao implements ResponseDAO {
    EntityManager manager = JPA.em();
    @Override
    public Response insert(Response resp) {
        resp.getAnswers().forEach(item->item.setResponse(resp));
        resp.getAnswers().forEach(answer->{
            answer.getOptions().forEach(option->option.getAnswerList().add(answer));
        });
        manager.persist(resp);
        return resp;
    }

    @Override
    public Response get(int id) {
        return manager.find(Response.class, id);
    }

    @Override
    public void update(Response obj) {
        manager.merge(obj);
    }

    @Override
    public void delete(int id) {
        manager.remove(manager.find(Response.class,id));
    }

    @Override
    public List<Response> getAll() {
        return manager.createQuery("Select e from Response e").getResultList();
    }

    @Override
    public long getResponseCount() {
        return (Long)manager.createQuery("Select Count(e) From Response e").getResultList().get(0);
    }

    @Override
    public void deleteAll() {
        manager.createQuery("Delete From Response e").executeUpdate();
    }
}
