package validators;

import models.entities.Field;

import java.util.ArrayList;
import java.util.List;

public class FieldValidator implements Validatable {

    @Override
    public List<CustomError> validate(Object obj) {
        return new ArrayList<>();
    }

}
