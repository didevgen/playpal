package validators;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation factory (Abstract factory implementation)
 */
public class ValidatorFactory {

    public static final String FIELD_VALIDATOR = "Field validator";
    public static final String RESPONSE_VALIDATOR = "Response validator";
    public Validatable getValidator(String key) {
        switch (key) {
            case FIELD_VALIDATOR :{
                return new FieldValidator();
            }
            case RESPONSE_VALIDATOR: {
                return new ResponseValidator();
            }
            default:{
                return new Validatable() {
                    @Override
                    public List<ValidationError> validate(Object obj) {
                        return new ArrayList<>();
                    }
                };
            }
        }
    }
}
