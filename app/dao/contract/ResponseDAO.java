package dao.contract;

import models.Response;

public interface ResponseDAO extends CRUD<Response>{
    long getResponseCount();
    void deleteAll();
}
