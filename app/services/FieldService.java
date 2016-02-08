package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.entities.Field;
import models.enums.Type;

import java.util.List;
import java.util.stream.Collectors;

public class FieldService {
    public List<Field> setFieldTypes(List<Field> src) {
        return src.stream().map(item -> {
            item.setType(Type.valueOf(item.getType().name()));
            return item;
        }).collect(Collectors.toList());
    }
    private boolean isNull(Object obj) {
        return obj==null;
    }
    public Field parseField(JsonNode json) {
        Field field = new Field();

        System.out.println(json.textValue());
        return field;
    }
}
