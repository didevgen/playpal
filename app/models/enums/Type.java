package models.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Type {
    SINGLE_LINE_TEXT("Single line text"), MULTI_LINE_TEXT("Textarea"),
    RADIO_BUTTON("Radio button",true), CHECK_BOX("Check box",true),
    COMBO_BOX("Combobox",true), DATE("Date"),SLIDER("Slider");

    private String value;
    private boolean hasOptions;
    Type() {
    }

    Type(String value) {
        this.value = value;
    }

    Type(String value, boolean hasOptions) {
        this.value = value;
        this.hasOptions = hasOptions;
    }

    public String getValue() {
        return value;
    }

    public String getTypeName(String value) {
        for (Type type : Type.values()) {
            if (type.getValue().equals(value)) {
                return type.name();
            }
        }
        return "N/A";
    }
    public static Map<String, Boolean> optionsToItem() {
        Map<String,Boolean> result = new HashMap<>();
        for (Type type : Type.values()) {
            result.put(type.getValue(),type.hasOptions);
        }
        return result;
    }
    public boolean hasOptions() {
        return hasOptions;
    }

    public static List<String> getAllValues() {
        List<String> result = new ArrayList<>();
        for (Type type : Type.values()) {
            result.add(type.getValue());
        }
        return result;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
