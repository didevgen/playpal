package dao.contract;

import java.util.List;

/**
 * Base CRUD interface
 * @param <T>
 */
public interface CRUD<T> {
    T insert(T obj);
    T get(int id);
    void update(T obj);
    void delete(int id);
    List<T> getAll();
}
