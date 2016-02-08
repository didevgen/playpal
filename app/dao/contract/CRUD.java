package dao.contract;

import java.util.List;

public interface CRUD<T> {
    T insert(T obj);
    T get(int id);
    void update(T obj);
    void delete(int id);
    List<T> getAll();
}
