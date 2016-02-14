package dao.contract;

import models.Field;

public interface FieldDAO extends CRUD<Field> {
    long getFieldsAmount();
}
