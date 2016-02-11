package validators;

import models.entities.Field;
import models.entities.Option;

import java.util.ArrayList;
import java.util.List;

public class FieldValidator implements Validatable {
    private CustomError checkLabelError(Field field) {
        if (field.getLabel().isEmpty()) {
            return generateError("Label is empty","label");
        }
        else {
            return null;
        }
    }
    private CustomError checkTypeError(Field field) {
        if (field.getType()==null) {
            return generateError("Type is empty","type");
        } else if (field.getType().hasOptions()&&field.getOptions().isEmpty()) {
            return generateError("Type has no options","type");
        }
        else {
            return null;
        }
    }
    private CustomError checkOptions(Option option) {
        if (option.getOptionValue().isEmpty()) {
            System.out.println("option is empty");
            return generateError("Option is empty","options");
        }
        else {
            return null;
        }
    }
    private CustomError generateError(String message, String fieldName) {
        CustomError error = new CustomError();
        error.setFieldName(fieldName);
        error.setMessage(message);
        return error;
    }
    private CustomError checkNullAndAdd(CustomError error, List<CustomError> errorList) {
        if (error!=null) {
            errorList.add(error);
            error=null;
        }
        return error;
    }
    @Override
    public List<CustomError> validate(Object obj) {
        List<CustomError> errorList = new ArrayList<>();
        Field field = (Field) obj;
        CustomError error = null;
        error = checkLabelError(field);
        checkNullAndAdd(error,errorList);
        error = checkTypeError(field);
        checkNullAndAdd(error,errorList);
        for(Option option:field.getOptions()) {
            error=checkOptions(option);
            error=checkNullAndAdd(error,errorList);
        }
        return errorList;
    }

}
