package dao.contract;

import models.Response;

public interface ResponseDAO extends CRUD<Response>{
    /**
     * Retrieves count of responses in the db
     * @return
     */
    long getResponseCount();

    /**
     * Clears the table of responses in case of 0-amount of fields
     */
    void deleteAll();
}
