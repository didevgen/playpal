package dao.realization.qdsl;

import com.mysema.query.jpa.EclipseLinkTemplates;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import dao.contract.FieldDAO;
import models.Field;
import models.QField;
import models.QOption;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;
import java.util.List;

public class QDslFieldDao implements FieldDAO{
    EntityManager manager = JPA.em();
    @Override
    public long getFieldsAmount() {
        QField field = QField.field;
        JPAQuery query = new JPAQuery(manager);
        return query.from(field).count();
    }

    @Override
    public Field insert(Field obj) {
        manager.persist(obj);
        return obj;
    }

    @Override
    public Field get(int id) {
        QField field = QField.field;
        JPQLQuery query = new JPAQuery(manager, EclipseLinkTemplates.DEFAULT);
        Field result = query.from(field)
                .where(field.fieldId.eq(id))
                .uniqueResult(field);
        return result;
    }

    @Override
    public void update(Field obj) {
        QOption opt= QOption.option;
        new JPADeleteClause(manager, opt).where(opt.field.fieldId.eq(obj.getFieldId())).execute();
        obj.getOptions().forEach(option -> option.setField(obj));
        manager.merge(obj);
    }

    @Override
    public void delete(int id) {
        manager.remove(manager.find(Field.class, id));
    }

    @Override
    public List<Field> getAll() {
        QField field = QField.field;
        JPQLQuery query = new JPAQuery (manager, EclipseLinkTemplates.DEFAULT);
        return query.from(field).list(field);
    }
}
