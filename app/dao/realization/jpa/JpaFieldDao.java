package dao.realization.jpa;

import dao.contract.FieldDAO;
import models.entities.Field;
import models.entities.Option;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaFieldDao implements FieldDAO{
    EntityManager manager = JPA.em();
    @Override
    public Field insert(Field obj) {
        manager.persist(obj);
        return obj;
    }

    @Override
    public Field get(int id) {
        return manager.find(Field.class, id);
    }

    @Override
    public void update(Field obj) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaDelete<Option> delete = cb.
                createCriteriaDelete(Option.class);
        Root e = delete.from(Option.class);
        delete.where(cb.equal(e.get("field"), obj));
        manager.createQuery(delete).executeUpdate();
        obj.getOptions().forEach(option -> option.setField(obj));
        manager.merge(obj);
    }

    @Override
    public void delete(int id) {
        manager.remove(manager.find(Field.class,id));
    }

    @Override
    public List<Field> getAll() {
       return manager.createQuery("Select e from Field e").getResultList();
    }
}
