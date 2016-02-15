package validators;

import java.util.List;

public interface Validatable {
    List<ValidationError> validate(Object obj);
}
