package dao.realization.qdsl;

import dao.contract.ResponseDAO;
import models.entities.Response;

import java.util.List;

public class QDslResponseDao implements ResponseDAO{
    @Override
    public long getResponseCount() {
        return 0;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Response insert(Response obj) {
        return null;
    }

    @Override
    public Response get(int id) {
        return null;
    }

    @Override
    public void update(Response obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Response> getAll() {
        return null;
    }
}
