package dao.realization.qdsl;

import com.mysema.query.jpa.EclipseLinkTemplates;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import dao.contract.ResponseDAO;
import models.Field;
import models.QField;
import models.QResponse;
import models.Response;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;
import java.util.List;

public class QDslResponseDao implements ResponseDAO{
    private EntityManager manager = JPA.em();
    @Override
    public long getResponseCount() {
        QResponse response = QResponse.response;
        JPAQuery query = new JPAQuery(manager);
        return query.from(response).count();
    }

    @Override
    public void deleteAll() {
        QResponse response = QResponse.response;
        new JPADeleteClause(manager, response).execute();
    }

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
        QResponse response = QResponse.response;
        JPQLQuery query = new JPAQuery(manager, EclipseLinkTemplates.DEFAULT);
        Response result = query.from(response)
                .where(response.responseId.eq(id))
                .uniqueResult(response);
        return result;
    }

    @Override
    public void update(Response obj) {
        manager.merge(obj);
    }

    @Override
    public void delete(int id) {
        QResponse response = QResponse.response;
        new JPADeleteClause(manager, response).where(response.responseId.eq(id)).execute();
    }

    @Override
    public List<Response> getAll() {
        QResponse response = QResponse.response;
        JPQLQuery query = new JPAQuery (manager, EclipseLinkTemplates.DEFAULT);
        return query.from(response).list(response);
    }
}
