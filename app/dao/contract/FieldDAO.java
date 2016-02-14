package dao.contract;

import models.Field;

public interface FieldDAO extends CRUD<Field> {
    /**
     * Returns count of fields in the db
     * @return
     */
    long getFieldsAmount();
}
