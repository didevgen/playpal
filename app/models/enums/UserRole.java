package models.enums;

public enum UserRole {
    ADMIN("Admin"), USER("User");
    private String value;
    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
