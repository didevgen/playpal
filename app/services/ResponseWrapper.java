package services;

import dao.DAOFactory;
import dao.contract.FieldDAO;
import dao.contract.ResponseDAO;
import models.Answer;
import models.Field;
import models.Response;
import play.db.jpa.JPA;
import play.libs.F;
import java.util.*;

public class ResponseWrapper {

    private Map<Integer, List<String>> result = new TreeMap<>();
    private long responseAmount;
    public  ResponseWrapper() {

    }
    public ResponseWrapper(boolean withResult) {
        JPA.withTransaction(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                FieldDAO fieldDAO = DAOFactory.getDAOFactory(DAOFactory.JPA).getFieldDAO();
                ResponseDAO responseDao = DAOFactory.getDAOFactory(DAOFactory.JPA).getResponseDAO();
                if (withResult) {
                    List<Response> responses = responseDao.getAll();
                    List<Field> fieldList = fieldDAO.getAll();
                    fillTheResult(responses, fieldList);
                } else {
                    responseAmount = responseDao.getResponseCount();
                }
            }
        });
    }

    public ResponseWrapper(Response response) {
        FieldDAO dao = DAOFactory.getDAOFactory(DAOFactory.JPA).getFieldDAO();
        ResponseDAO respDao = DAOFactory.getDAOFactory(DAOFactory.JPA).getResponseDAO();
        List<Field> fieldList = dao.getAll();
        List<String> resultList = new ArrayList<>();
        for (Field field : fieldList) {
            resultList.add(findFieldValueInResponse(field, response));
        }
        responseAmount = respDao.getResponseCount();
        result.put(response.getResponseId(), resultList);
    }

    private String findFieldValueInResponse(Field field, Response response) {
        for (Answer answer : response.getAnswers()) {
            if (answer.getField().getFieldId() == field.getFieldId()) {
                return answer.getValue().equals("")?"N/A":answer.getValue();
            }
        }
        return "N/A";
    }

    private void fillTheResult(List<Response> responses, List<Field> fields) {
        for (Response response : responses) {
            List<String> resultList = new ArrayList<>();
            for (Field field : fields) {
                resultList.add(findFieldValueInResponse(field, response));
            }
            result.put(response.getResponseId(), resultList);
        }
    }

    public Map<Integer, List<String>> getResult() {
        return result;
    }

    public void setResult(Map<Integer, List<String>> result) {
        this.result = result;
    }

    public long getResponseAmount() {
        return responseAmount;
    }

    public void setResponseAmount(long responseAmount) {
        this.responseAmount = responseAmount;
    }
}
