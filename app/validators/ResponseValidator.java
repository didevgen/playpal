package validators;

import models.Answer;
import models.Response;

import java.util.ArrayList;
import java.util.List;

public class ResponseValidator implements Validatable{
    /**
     * Validates the response for errors
     * @param obj must be models.Response
     * @return
     */
    @Override
    public List<CustomError> validate(Object obj) {
        List<CustomError> errors = new ArrayList<>();
        Response resp = (Response)obj;
        List<Answer> answers = resp.getAnswers();
        for(Answer answer : answers) {
            if (answer.getField().isRequired() && answer.getValue().isEmpty()) {
                CustomError error = new CustomError();
                error.setFieldName(answer.getFieldName());
                error.setMessage("Field is required and must be typed");
                errors.add(error);
            }
        }
        return errors;
    }
}
