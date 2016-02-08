package dao.contract;

import models.entities.Response;

public interface ResponseDAO extends CRUD<Response>{
    long getResponseCount();
}
