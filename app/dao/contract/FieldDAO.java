package dao.contract;

import models.entities.Field;

public interface FieldDAO extends CRUD<Field> {
    long getFieldsAmount();
}
