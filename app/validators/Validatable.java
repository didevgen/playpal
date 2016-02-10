package validators;

import java.util.List;

public interface Validatable {
    List<CustomError> validate(Object obj);
}
