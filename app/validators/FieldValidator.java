package validators;

import models.Field;
import models.Option;
import models.Type;

import java.util.ArrayList;
import java.util.List;

public class FieldValidator implements Validatable {
    private ValidationError checkLabelError(Field field) {
        if (field.getLabel().isEmpty()) {
            return generateError("Label is empty", "label");
        } else {
            return null;
        }
    }

    private ValidationError checkTypeError(Field field) {
        if (field.getType() == null) {
            return generateError("Type is empty", "type");
        } else if (field.getType().hasOptions() && field.getOptions().isEmpty()) {
            return generateError("Type has no options", "type");
        } else {
            return null;
        }
    }

    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String getFieldOptionValue(int number, Field field) {
        return field.getOptions().get(number).getOptionValue();
    }

    private List<ValidationError> checkSliderError(Field field) {
        List<ValidationError> errorList = new ArrayList<>();
        if (field.getType()!=null && field.getType().equals(Type.SLIDER)) {
            if (field.getOptions().size() != 2) {
                errorList.add(generateError("Slider must have min and max value", "type"));
            } else {
                if (!(tryParseInt(getFieldOptionValue(0, field))
                        && tryParseInt(getFieldOptionValue(1, field)))) {
                    errorList.add(generateError("Slider must have only digital values", "type"));
                } else if (Integer.valueOf(getFieldOptionValue(0, field)) >= Integer.valueOf(getFieldOptionValue(1, field))) {
                    errorList.add(generateError("Min value can't be greater than max value", "type"));
                }
            }
        }
        return errorList;
    }

    private ValidationError checkOptions(Option option) {
        if (option.getOptionValue().isEmpty()) {
            return generateError("Option is empty", "options");
        } else {
            return null;
        }
    }

    private ValidationError generateError(String message, String fieldName) {
        ValidationError error = new ValidationError();
        error.setFieldName(fieldName);
        error.setMessage(message);
        return error;
    }

    private ValidationError checkNullAndAdd(ValidationError error, List<ValidationError> errorList) {
        if (error != null) {
            errorList.add(error);
            error = null;
        }
        return error;
    }

    /**
     * Validates field and returns error list
     * @param obj must be models.Field
     * @return List of the errors which will be shown in case of bad request
     */
    @Override
    public List<ValidationError> validate(Object obj) {
        List<ValidationError> errorList = new ArrayList<>();
        Field field = (Field) obj;
        ValidationError error = null;
        error = checkLabelError(field);
        checkNullAndAdd(error, errorList);
        error = checkTypeError(field);
        checkNullAndAdd(error, errorList);
        for (Option option : field.getOptions()) {
            error = checkOptions(option);
            error = checkNullAndAdd(error, errorList);
        }
        errorList.addAll(checkSliderError(field));
        return errorList;
    }

}
