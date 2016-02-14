package validators;

/**
 * Entity for validation errors
 */
public class CustomError {
    private String message;
    private String causedBy;
    private String fieldName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCausedBy() {
        return causedBy;
    }

    public void setCausedBy(String causedBy) {
        this.causedBy = causedBy;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
