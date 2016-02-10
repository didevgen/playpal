package validators;

import java.util.ArrayList;
import java.util.List;

public class ResponseValidator implements Validatable{
    @Override
    public List<CustomError> validate(Object obj) {
        return new ArrayList<>();
    }
}
